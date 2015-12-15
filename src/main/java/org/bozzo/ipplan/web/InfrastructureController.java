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

import org.bozzo.ipplan.domain.dao.InfrastructureRepository;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/infras")
public class InfrastructureController {
	private static Logger LOGGER = LoggerFactory.getLogger(InfrastructureController.class);
	
	@Autowired
	private InfrastructureRepository repository;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Iterable<Infrastructure> getInfrastructures() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{infraId}", method=RequestMethod.GET)
	public Infrastructure getInfrastructure(@PathVariable Integer infraId) {
		return repository.findOne(infraId);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Infrastructure addInfrastructure(@RequestBody @NotNull Infrastructure infra) {
		LOGGER.info("add new infrastruture: {}", infra);
		return repository.save(infra);
	}

	@RequestMapping(value = "/{infraId}", method=RequestMethod.PUT)
	public Infrastructure updateInfrastructure(@PathVariable Integer infraId, @RequestBody @NotNull Infrastructure infra) {
		Preconditions.checkArgument(infraId.equals(infra.getId()));
		LOGGER.info("update infrastruture: {}", infra);
		return repository.save(infra);
	}

	@RequestMapping(value = "/{infraId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteInfrastructure(@PathVariable Integer infraId) {
		LOGGER.info("delete infrastruture with id: {}", infraId);
		repository.delete(infraId);
	}
}
