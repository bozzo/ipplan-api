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

import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.ui.SubnetResource;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

/**
 * @author boris
 *
 */
@RestController
@RequestMapping("/api/infras/{infraId}/subnets")
public class SubnetController {
	private static Logger LOGGER = LoggerFactory.getLogger(SubnetController.class);
	
	@Autowired
	private SubnetRepository repository;
	
	@Autowired
	private SubnetResourceAssembler assembler;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public PagedResources<SubnetResource> getSubnets(@PathVariable @NotNull Integer infraId, Pageable pageable, PagedResourcesAssembler<Subnet> pagedAssembler) {
		Page<Subnet> subnets = this.repository.findByInfraId(infraId, pageable);
		return pagedAssembler.toResource(subnets, assembler);
	}

	@RequestMapping(value = "/{subnetId}", method = RequestMethod.GET)
	public HttpEntity<SubnetResource> getSubnet(@PathVariable @NotNull Integer infraId, @PathVariable Long subnetId) {
		Subnet subnet = repository.findByInfraIdAndId(infraId, subnetId);
		if (subnet == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(subnet), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public HttpEntity<SubnetResource> addSubnet(@PathVariable @NotNull Integer infraId, @RequestBody @NotNull Subnet subnet) {
		Preconditions.checkArgument(infraId.equals(subnet.getInfraId()));
		LOGGER.info("add new subnet: {}", subnet);
		Subnet sub = repository.save(subnet);
		if (sub == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(sub), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{subnetId}", method = RequestMethod.PUT)
	public HttpEntity<SubnetResource> updateSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId, @RequestBody @NotNull Subnet subnet) {
		Preconditions.checkArgument(infraId.equals(subnet.getInfraId()));
		Preconditions.checkArgument(subnetId.equals(subnet.getId()));
		LOGGER.info("update subnet: {}", subnet);
		Subnet sub = repository.save(subnet);
		if (sub == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(sub), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{subnetId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId) {
		LOGGER.info("delete subnet with id: {} (infra id: {})", subnetId, infraId);
		this.repository.deleteByInfraIdAndId(infraId, subnetId);
	}
}
