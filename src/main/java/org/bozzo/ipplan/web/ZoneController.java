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
import org.bozzo.ipplan.domain.exception.NotYetImplementedException;
import org.bozzo.ipplan.domain.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	private ZoneRepository repository;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Iterable<Zone> getZones(@PathVariable @NotNull Integer infraId) {
		return repository.findByInfraId(infraId);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.GET)
	public Zone getZone(@PathVariable Integer infraId, @PathVariable Integer zoneId) {
		return repository.findByInfraIdAndId(infraId, zoneId);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Zone addZone(@PathVariable Integer infraId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		return repository.save(zone);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.PUT)
	public Zone updateZone(@PathVariable Integer infraId, @PathVariable Integer zoneId, @RequestBody @NotNull Zone zone) {
		Preconditions.checkArgument(infraId.equals(zone.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(zone.getId()));
		return repository.save(zone);
	}

	@RequestMapping(value = "/{zoneId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteZone(@PathVariable Integer infraId, @PathVariable Integer zoneId) {
		throw new NotYetImplementedException();
	}
}
