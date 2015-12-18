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
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.web.assembler.InfrastructureResourceAssembler;
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
	
	@Autowired
	private InfrastructureResourceAssembler assembler;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public PagedResources<InfrastructureResource> getInfrastructures(@RequestParam(required=false) String group, Pageable pageable, PagedResourcesAssembler<Infrastructure> pagedAssembler) {
		Page<Infrastructure> infras = null;
		if (group != null) {
			infras = repository.findAllByGroup(group, pageable);
		} else {
			infras = repository.findAll(pageable);
		}
		return pagedAssembler.toResource(infras, assembler);
	}

	@RequestMapping(value = "/{infraId}", method = RequestMethod.GET)
	public HttpEntity<InfrastructureResource> getInfrastructure(@PathVariable Integer infraId) {
		Infrastructure infra = repository.findOne(infraId);
		if (infra == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(infra), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public HttpEntity<InfrastructureResource> addInfrastructure(@RequestBody @NotNull Infrastructure infra) {
		LOGGER.info("add new infrastruture: {}", infra);
		Infrastructure infrastructure = repository.save(infra);
		if (infrastructure == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(infra), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{infraId}", method = RequestMethod.PUT)
	public HttpEntity<InfrastructureResource> updateInfrastructure(@PathVariable Integer infraId,
			@RequestBody @NotNull Infrastructure infra) {
		Preconditions.checkArgument(infraId.equals(infra.getId()));
		LOGGER.info("update infrastruture: {}", infra);
		Infrastructure infrastructure = repository.save(infra);
		if (infrastructure == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(infra), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{infraId}", method = RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteInfrastructure(@PathVariable Integer infraId) {
		LOGGER.info("delete infrastruture with id: {}", infraId);
		repository.delete(infraId);
	}
}
