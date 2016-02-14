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
import org.bozzo.ipplan.domain.dao.AddressPlanRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.AddressPlan;
import org.bozzo.ipplan.domain.model.ui.AddressPlanResource;
import org.bozzo.ipplan.web.assembler.AddressPlanResourceAssembler;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

/**
 * @author boris
 *
 */
@RestController
@RequestMapping("/api/infras/{infraId}/plan")
public class AddressPlanController {
	private static Logger logger = LoggerFactory.getLogger(AddressPlanController.class);
	
	@Autowired
	private AddressPlanRepository repository;
	
	@Autowired
	private AddressPlanResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<PagedResources<AddressPlanResource>> getAddressPlans(@PathVariable @NotNull Integer infraId, Pageable pageable,
			PagedResourcesAssembler<AddressPlan> pagedAssembler) {
		Page<AddressPlan> subnets = this.repository.findAllByInfraId(infraId, pageable);
		PagedResources<AddressPlanResource> page = pagedAssembler.toResource(subnets, assembler);
		page.add(InfrastructureResourceAssembler.link(infraId));
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@RequestMapping(value = "/{planId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<AddressPlanResource> getAddressPlan(@PathVariable @NotNull Integer infraId, @PathVariable Long planId) {
		AddressPlan plan = this.repository.findByInfraIdAndId(infraId, planId);
		if (plan == null) {
			throw new ApiException(ApiError.PLAN_NOT_FOUND);
		}
		return new ResponseEntity<>(assembler.toResource(plan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<AddressPlanResource> addAddressPlan(@PathVariable @NotNull Integer infraId,
			@RequestBody @NotNull AddressPlan plan) {
		Preconditions.checkArgument(infraId.equals(plan.getInfraId()));
		logger.info("add new address plan: {}", plan);

		AddressPlan ap = this.repository.save(plan);
		return new ResponseEntity<>(assembler.toResource(ap), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{planId}", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<AddressPlanResource> updateAddressPlan(@PathVariable Integer infraId, @PathVariable Long planId,
			@RequestBody @NotNull AddressPlan plan) {
		Preconditions.checkArgument(infraId.equals(plan.getInfraId()));
		Preconditions.checkArgument(planId.equals(plan.getId()));
		logger.info("update address plan: {}", plan);
		
		AddressPlan ap = this.repository.save(plan);
		return new ResponseEntity<>(assembler.toResource(ap), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{planId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAddressPlan(@PathVariable Integer infraId, @PathVariable Long planId) {
		logger.info("delete subnet with id: {} (infra id: {})", planId, infraId);
		this.repository.deleteByInfraIdAndId(infraId, planId);
	}
}
