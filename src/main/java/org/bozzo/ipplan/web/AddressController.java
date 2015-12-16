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

import org.bozzo.ipplan.domain.dao.AddressRepository;
import org.bozzo.ipplan.domain.model.Address;
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
@RequestMapping("/api/infras/{infraId}/subnets/{subnetId}/addresses")
public class AddressController {
	private static Logger LOGGER = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private AddressRepository repository;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Iterable<Address> getAddresses(@PathVariable Integer infraId, @PathVariable Long subnetId) {
		return this.repository.findBySubnetId(subnetId);
	}

	@RequestMapping(value = "/{ip}", method=RequestMethod.GET)
	public Address getAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip) {
		return this.repository.findBySubnetIdAndIp(subnetId, ip);
	}

	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Address addAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @RequestBody @NotNull Address address) {
		Preconditions.checkArgument(subnetId.equals(address.getSubnetId()));
		LOGGER.info("add new address: {}", address);
		return this.repository.save(address);
	}

	@RequestMapping(value = "/{ip}", method=RequestMethod.PUT)
	public Address updateAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip, @RequestBody @NotNull Address address) {
		Preconditions.checkArgument(subnetId.equals(address.getSubnetId()));
		Preconditions.checkArgument(ip.equals(address.getIp()));
		LOGGER.info("update address: {}", address);
		return this.repository.save(address);
	}

	@RequestMapping(value = "/{addressId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long addressId) {
		LOGGER.info("delete address with id: {} (infra id: {}, subnet id: {})", addressId, infraId, subnetId);
		this.repository.deleteBySubnetIdAndIp(subnetId, addressId);
	}
}
