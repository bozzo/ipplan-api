/*
 * Copyright (C) 2015
 *     Boris Barnier <b.barnier@gmail.com>
 *   
 * This file is part of ipplan-api.
 * 
 * ipplan-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ipplan-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ipplan-api.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.bozzo.ipplan.web;

import javax.validation.constraints.NotNull;

import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.DeleteMode;
import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.ui.SubnetResource;
import org.bozzo.ipplan.domain.service.SubnetService;
import org.bozzo.ipplan.tools.IpAddress;
import org.bozzo.ipplan.tools.Netmask;
import org.bozzo.ipplan.web.assembler.InfrastructureResourceAssembler;
import org.bozzo.ipplan.web.assembler.SubnetResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Preconditions;
import com.mangofactory.swagger.annotations.ApiIgnore;

/**
 * @author boris
 *
 */
@RestController
@RequestMapping("/api/infras/{infraId}/subnets")
public class SubnetController {
	private static Logger logger = LoggerFactory.getLogger(SubnetController.class);

	@Autowired
	private SubnetService service;

	@Autowired
	private SubnetRepository repository;

	@Autowired
	private SubnetResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.TEXT_HTML_VALUE })
	@ApiIgnore
	public ModelAndView getSubnetsView(@RequestParam(required = false) String ip,
			@RequestParam(required = false) Long size, @PathVariable @NotNull Integer infraId, Pageable pageable,
			PagedResourcesAssembler<Subnet> pagedAssembler) {
		HttpEntity<PagedResources<SubnetResource>> subnets = this.getSubnets(ip, size, infraId, pageable,
				pagedAssembler);
		ModelAndView view = new ModelAndView("subnets");
		view.addObject("pages", subnets.getBody());
		return view;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<PagedResources<SubnetResource>> getSubnets(@RequestParam(required = false) String ip,
			@RequestParam(required = false) Long size, @PathVariable @NotNull Integer infraId, Pageable pageable,
			PagedResourcesAssembler<Subnet> pagedAssembler) {
		Page<Subnet> subnets;
		if (ip != null) {
			long ip4 = IpAddress.toLong(ip);
			if (size == null) {
				subnets = this.repository.findAllByInfraIdAndIp(infraId, ip4, pageable);
			} else {
				subnets = this.repository.findAllByInfraIdAndIpAndSize(infraId, ip4, size, pageable);
			}
		} else {
			subnets = this.repository.findAllByInfraId(infraId, pageable);
		}
		PagedResources<SubnetResource> page = pagedAssembler.toResource(subnets, assembler);
		page.add(InfrastructureResourceAssembler.link(infraId));
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@RequestMapping(value = "/{subnetId}", method = RequestMethod.GET, produces = { MediaType.TEXT_HTML_VALUE })
	@ApiIgnore
	public ModelAndView getSubnetView(@PathVariable @NotNull Integer infraId, @PathVariable Long subnetId) {
		HttpEntity<SubnetResource> subnet = this.getSubnet(infraId, subnetId);
		ModelAndView view = new ModelAndView("subnet");
		view.addObject("id", subnetId);
		view.addObject("object", subnet.getBody());
		return view;
	}

	@RequestMapping(value = "/{subnetId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<SubnetResource> getSubnet(@PathVariable @NotNull Integer infraId, @PathVariable Long subnetId) {
		Subnet subnet = repository.findByInfraIdAndId(infraId, subnetId);
		if (subnet == null) {
			throw new ApiException(ApiError.SUBNET_NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(subnet), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<SubnetResource> addSubnet(@PathVariable @NotNull Integer infraId,
			@RequestBody @NotNull Subnet subnet) {
		Preconditions.checkArgument(infraId.equals(subnet.getInfraId()));
		logger.info("add new subnet: {}", subnet);
		
		if (! Netmask.isValidNetmask(subnet.getSize())) {
			throw new ApiException(ApiError.BAD_NETMASK);
		} else if (! IpAddress.isNetworkAddress(subnet.getIp(), subnet.getSize())) {
			throw new ApiException(ApiError.BAD_NETWORK);
		}
		
		Subnet sub = service.save(subnet);
		return new ResponseEntity<>(assembler.toResource(sub), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{subnetId}", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<SubnetResource> updateSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId,
			@RequestBody @NotNull Subnet subnet) {
		Preconditions.checkArgument(infraId.equals(subnet.getInfraId()));
		Preconditions.checkArgument(subnetId.equals(subnet.getId()));
		logger.info("update subnet: {}", subnet);
		
		if (! Netmask.isValidNetmask(subnet.getSize())) {
			throw new ApiException(ApiError.BAD_NETMASK);
		} else if (! IpAddress.isNetworkAddress(subnet.getIp(), subnet.getSize())) {
			throw new ApiException(ApiError.BAD_NETWORK);
		}
		
		Subnet sub = service.save(subnet);
		return new ResponseEntity<>(assembler.toResource(sub), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{subnetId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId, @RequestParam(required = false) DeleteMode mode) {
		logger.info("delete subnet with id: {} (infra id: {})", subnetId, infraId);
		this.service.deleteByInfraIdAndId(mode, infraId, subnetId);
	}
}
