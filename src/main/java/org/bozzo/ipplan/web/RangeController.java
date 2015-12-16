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
import org.bozzo.ipplan.domain.dao.RangeRepository;
import org.bozzo.ipplan.domain.model.Range;
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
@RequestMapping("/api/infras/{infraId}/zones/{zoneId}/ranges")
public class RangeController {
	private static Logger LOGGER = LoggerFactory.getLogger(RangeController.class);
	
	@Autowired
	private RangeRepository repository;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Page<Range> getRanges(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestParam(required=false) Integer number, @RequestParam(required=false) Integer size) {
		if (number == null) number=0;
		if (size == null) size=IpplanConfig.DEFAULT_MAX_API_RESULT;
		return this.repository.findByInfraIdAndZoneId(infraId, zoneId, new PageRequest(number, size));
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.GET)
	public Range getRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId) {
		return this.repository.findByInfraIdAndZoneIdAndId(infraId, zoneId, rangeId);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Range addRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @RequestBody @NotNull Range range) {
		Preconditions.checkArgument(infraId.equals(range.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(range.getZoneId()));
		LOGGER.info("add new range: {}", range);
		return this.repository.save(range);
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.PUT)
	public Range updateRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId, @RequestBody @NotNull Range range) {
		Preconditions.checkArgument(infraId.equals(range.getInfraId()));
		Preconditions.checkArgument(zoneId.equals(range.getZoneId()));
		Preconditions.checkArgument(rangeId.equals(range.getId()));
		LOGGER.info("update range: {}", range);
		return this.repository.save(range);
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteRange(@PathVariable Integer infraId, @PathVariable Long zoneId, @PathVariable Long rangeId) {
		LOGGER.info("delete range with id: {} (infra id: {}, zone id: {})", rangeId, infraId, zoneId);
		this.repository.deleteByInfraIdAndZoneIdAndId(infraId, zoneId, rangeId);
	}
}
