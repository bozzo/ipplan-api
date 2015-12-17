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

import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.ZoneResource;
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
@RequestMapping("/api/infras/{infraId}/zones")
public class ZoneController {
	private static Logger LOGGER = LoggerFactory.getLogger(ZoneController.class);
	
	@Autowired
	private ZoneRepository repository;
	
	@Autowired
	private ZoneResourceAssembler assembler;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public PagedResources<ZoneResource> getZones(@PathVariable @NotNull Integer infraId, Pageable pageable, PagedResourcesAssembler<Zone> pagedAssembler) {
		Page<Zone> zones = this.repository.findByInfraId(infraId, pageable);
		return pagedAssembler.toResource(zones, assembler);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.GET)
	public HttpEntity<ZoneResource> getZone(@PathVariable Integer infraId, @PathVariable Long zoneId) {
		Zone zone = repository.findByInfraIdAndId(infraId, zoneId);
		if (zone == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(zone), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public HttpEntity<ZoneResource> addZone(@PathVariable Integer infraId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		LOGGER.info("add new zone: {}", zone);
		Zone zon = repository.save(zone);
		if (zon == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(zon), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.PUT)
	public HttpEntity<ZoneResource> updateZone(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(zone.getId()));
		LOGGER.info("update zone: {}", zone);
		Zone zon = repository.save(zone);
		if (zon == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(zon), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteZone(@PathVariable Integer infraId, @PathVariable Long zoneId) {
		LOGGER.info("delete zone with id: {} (infra id: {})", zoneId, infraId);
		this.repository.deleteByInfraIdAndId(infraId, zoneId);
	}
}
