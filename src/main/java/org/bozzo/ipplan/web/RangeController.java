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
import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.dao.RangeRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.ui.RangeResource;
import org.bozzo.ipplan.domain.service.RangeService;
import org.bozzo.ipplan.tools.IpAddress;
import org.bozzo.ipplan.tools.Netmask;
import org.bozzo.ipplan.web.assembler.RangeResourceAssembler;
import org.bozzo.ipplan.web.assembler.ZoneResourceAssembler;
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

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author boris
 *
 */
@RestController
@RequestMapping("/api/infras/{infraId}/zones/{zoneId}/ranges")
public class RangeController {
	private static Logger logger = LoggerFactory.getLogger(RangeController.class);
	
	@Autowired
	private RangeService service;
	
	@Autowired
	private RangeRepository repository;
	
	@Autowired
	private RangeResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	@ApiIgnore
	public ModelAndView getRangesView(@PathVariable Integer infraId, @PathVariable Long zoneId, Pageable pageable, PagedResourcesAssembler<Range> pagedAssembler) {
		PagedResources<RangeResource> ranges = this.getRanges(infraId, zoneId, pageable, pagedAssembler);
		ModelAndView view = new ModelAndView("ranges");
		view.addObject("pages", ranges);
		return view;
	}

	@RequestMapping(method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public PagedResources<RangeResource> getRanges(@PathVariable Integer infraId, @PathVariable Long zoneId, Pageable pageable, PagedResourcesAssembler<Range> pagedAssembler) {
		Page<Range> ranges = this.repository.findByInfraIdAndZoneId(infraId, zoneId, pageable);
		PagedResources<RangeResource> page = pagedAssembler.toResource(ranges, assembler);
		page.add(ZoneResourceAssembler.link(infraId, zoneId));
		return page;
	}

	@RequestMapping(value = "/{rangeId}", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	@ApiIgnore
	public ModelAndView getRangeView(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId, @RequestParam(required=false) RequestMode mode) {
		HttpEntity<RangeResource> range = this.getRange(infraId, zoneId, rangeId, mode);
		ModelAndView view = new ModelAndView("range");
		view.addObject("id", rangeId);
		view.addObject("object", range.getBody());
		return view;
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<RangeResource> getRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId, @RequestParam(required=false) RequestMode mode) {
		Range range = this.service.findByInfraIdAndZoneIdAndId(infraId, zoneId, rangeId, mode);
		if (range == null) {
			throw new ApiException(ApiError.RANGE_NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(range), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<RangeResource> addRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestBody @NotNull Range range) {
		Preconditions.checkArgument(infraId.equals(range.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(range.getZoneId()));
		
		if (! Netmask.isValidNetmask(range.getSize())) {
			throw new ApiException(ApiError.BAD_NETMASK);
		} else if (! IpAddress.isNetworkAddress(range.getIp(), range.getSize())) {
			throw new ApiException(ApiError.BAD_NETWORK);
		}
		
		logger.info("add new range: {}", range);
		Range rang = service.save(range);
		return new ResponseEntity<>(assembler.toResource(rang), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<RangeResource> updateRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId, @RequestBody @NotNull Range range) {
		Preconditions.checkArgument(infraId.equals(range.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(range.getZoneId()));
		Preconditions.checkArgument(rangeId.equals(range.getId()));
		
		if (! Netmask.isValidNetmask(range.getSize())) {
			throw new ApiException(ApiError.BAD_NETMASK);
		} else if (! IpAddress.isNetworkAddress(range.getIp(), range.getSize())) {
			throw new ApiException(ApiError.BAD_NETWORK);
		}
		
		logger.info("update range: {}", range);
		Range rang = service.save(range);
		return new ResponseEntity<>(assembler.toResource(rang), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void deleteRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId) {
		logger.info("delete range with id: {} (infra id: {}, zone id: {})", rangeId, infraId, zoneId);
		this.repository.deleteByInfraIdAndZoneIdAndId(infraId, zoneId, rangeId);
	}
}
