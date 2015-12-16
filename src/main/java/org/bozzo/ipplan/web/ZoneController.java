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

import org.bozzo.ipplan.config.IpplanConfig;
import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.model.Zone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Page<Zone> getZones(@PathVariable @NotNull Integer infraId, @RequestParam(required=false) Integer number, @RequestParam(required=false) Integer size) {
		if (number == null) number=0;
		if (size == null) size=IpplanConfig.DEFAULT_MAX_API_RESULT;
		return repository.findByInfraId(infraId, new PageRequest(number, size));
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.GET)
	public Zone getZone(@PathVariable Integer infraId, @PathVariable Long zoneId) {
		return repository.findByInfraIdAndId(infraId, zoneId);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Zone addZone(@PathVariable Integer infraId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		LOGGER.info("add new zone: {}", zone);
		return repository.save(zone);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.PUT)
	public Zone updateZone(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(zone.getId()));
		LOGGER.info("update zone: {}", zone);
		return repository.save(zone);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteZone(@PathVariable Integer infraId, @PathVariable Long zoneId) {
		LOGGER.info("delete zone with id: {} (infra id: {})", zoneId, infraId);
		this.repository.deleteByInfraIdAndId(infraId, zoneId);
	}
}
