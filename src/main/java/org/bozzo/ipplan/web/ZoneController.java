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
import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.ZoneResource;
import org.bozzo.ipplan.domain.service.ZoneService;
import org.bozzo.ipplan.web.assembler.InfrastructureResourceAssembler;
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
@RequestMapping("/api/infras/{infraId}/zones")
public class ZoneController {
	private static Logger logger = LoggerFactory.getLogger(ZoneController.class);
	
	@Autowired
	private ZoneRepository repository;
	
	@Autowired
	private ZoneService service;
	
	@Autowired
	private ZoneResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	@ApiIgnore
	public ModelAndView getZonesView(@PathVariable @NotNull Integer infraId, Pageable pageable, PagedResourcesAssembler<Zone> pagedAssembler) {
		PagedResources<ZoneResource> subnets = this.getZones(infraId, pageable, pagedAssembler);
		ModelAndView view = new ModelAndView("zones");
		view.addObject("pages", subnets);
		return view;
	}

	@RequestMapping(method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public PagedResources<ZoneResource> getZones(@PathVariable @NotNull Integer infraId, Pageable pageable, PagedResourcesAssembler<Zone> pagedAssembler) {
		Page<Zone> zones = this.repository.findByInfraId(infraId, pageable);
		PagedResources<ZoneResource> page = pagedAssembler.toResource(zones, assembler);
		page.add(InfrastructureResourceAssembler.link(infraId));
		return page;
	}

	@RequestMapping(value = "/{zoneId}", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	@ApiIgnore
	public ModelAndView getZoneView(@PathVariable @NotNull Integer infraId, @PathVariable Long zoneId, @RequestParam(required=false) RequestMode mode) {
		HttpEntity<ZoneResource> zone = this.getZone(infraId, zoneId, mode);
		ModelAndView view = new ModelAndView("zone");
		view.addObject("id", zoneId);
		view.addObject("object", zone.getBody());
		return view;
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<ZoneResource> getZone(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestParam(required=false) RequestMode mode) {
		Zone zone = this.service.findByInfraIdAndId(infraId, zoneId, mode);
		if (zone == null) {
			throw new ApiException(ApiError.ZONE_NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(zone), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<ZoneResource> addZone(@PathVariable Integer infraId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		logger.info("add new zone: {}", zone);
		Zone zon = repository.save(zone);
		return new ResponseEntity<>(assembler.toResource(zon), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<ZoneResource> updateZone(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(zone.getId()));
		logger.info("update zone: {}", zone);
		Zone zon = repository.save(zone);
		return new ResponseEntity<>(assembler.toResource(zon), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteZone(@PathVariable Integer infraId, @PathVariable Long zoneId) {
		logger.info("delete zone with id: {} (infra id: {})", zoneId, infraId);
		this.repository.deleteByInfraIdAndId(infraId, zoneId);
	}
}
