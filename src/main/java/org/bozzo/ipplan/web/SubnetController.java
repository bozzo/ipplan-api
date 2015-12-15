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

import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.model.Subnet;
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
@RequestMapping("/api/infras/{infraId}/subnets")
public class SubnetController {
	private static Logger LOGGER = LoggerFactory.getLogger(SubnetController.class);
	
	@Autowired
	private SubnetRepository repository;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Iterable<Subnet> getSubnets(@PathVariable @NotNull Integer infraId) {
		return repository.findByInfraId(infraId);
	}

	@RequestMapping(value = "/{subnetId}", method=RequestMethod.GET)
	public Subnet getSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId) {
		return repository.findByInfraIdAndId(infraId, subnetId);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Subnet addSubnet(@PathVariable Integer infraId, @RequestBody @NotNull Subnet subnet) {
		Preconditions.checkArgument(infraId.equals(subnet.getInfraId()));
		LOGGER.info("add new subnet: {}", subnet);
		return repository.save(subnet);
	}

	@RequestMapping(value = "/{subnetId}", method=RequestMethod.PUT)
	public Subnet updateSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId, @RequestBody @NotNull Subnet subnet) {
		Preconditions.checkArgument(infraId.equals(subnet.getInfraId()));
		Preconditions.checkArgument(subnetId.equals(subnet.getId()));
		LOGGER.info("update subnet: {}", subnet);
		return repository.save(subnet);
	}

	@RequestMapping(value = "/{subnetId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteSubnet(@PathVariable Integer infraId, @PathVariable Long subnetId) {
		LOGGER.info("delete subnet with id: {} (infra id: {})", subnetId, infraId);
		this.repository.deleteByInfraIdAndId(infraId, subnetId);
	}
}
