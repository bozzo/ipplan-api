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

import java.util.Collections;
import java.util.List;

import org.bozzo.ipplan.domain.exception.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author boris
 *
 */
@RestController
@RequestMapping("/api/infras/{infraId}/zones/{zoneId}/ranges")
public class RangeController {

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<String> getRanges(@PathVariable Integer infraId, @PathVariable Integer zoneId) {
		return Collections.singletonList("Pri");
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.GET)
	public List<String> getRange(@PathVariable Integer infraId, @PathVariable Integer zoneId, @PathVariable String rangeId) {
		return Collections.singletonList("Pri");
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public List<String> addRange(@PathVariable Integer infraId, @PathVariable Integer zoneId) {
		throw new NotYetImplementedException();
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.PUT)
	public List<String> updateRange(@PathVariable Integer infraId, @PathVariable Integer zoneId, @PathVariable String rangeId) {
		throw new NotYetImplementedException();
	}

	@RequestMapping(value = "/{rangeId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteRange(@PathVariable Integer infraId, @PathVariable Integer zoneId, @PathVariable String rangeId) {
		throw new NotYetImplementedException();
	}
}
