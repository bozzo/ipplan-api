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
import org.bozzo.ipplan.domain.model.ui.AddressResource;
import org.bozzo.ipplan.web.assembler.AddressResourceAssembler;
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
import org.springframework.web.servlet.ModelAndView;

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
	
	@Autowired
	private AddressResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	public ModelAndView getAddressesView(@PathVariable Integer infraId, @PathVariable Long subnetId, Pageable pageable, PagedResourcesAssembler<Address> pagedAssembler) {
		PagedResources<AddressResource> addresses = this.getAddresses(infraId, subnetId, pageable, pagedAssembler);
		ModelAndView view = new ModelAndView("addresses");
		view.addObject("pages", addresses);
		return view;
	}

	@RequestMapping(method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public PagedResources<AddressResource> getAddresses(@PathVariable Integer infraId, @PathVariable Long subnetId, Pageable pageable, PagedResourcesAssembler<Address> pagedAssembler) {
		Page<Address> addresses = this.repository.findBySubnetId(subnetId, pageable);
		for (Address address : addresses) {
			address.setInfraId(infraId);
		}
		return pagedAssembler.toResource(addresses, assembler);
	}

	@RequestMapping(value = "/{ip}", method=RequestMethod.GET)
	public HttpEntity<AddressResource> getAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip) {
		Address address = this.repository.findBySubnetIdAndIp(subnetId, ip);
		if (address == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		address.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(address), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<AddressResource> addAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @RequestBody @NotNull Address address) {
		Preconditions.checkArgument(infraId.equals(address.getInfraId()));
		Preconditions.checkArgument(subnetId.equals(address.getSubnetId()));
		LOGGER.info("add new address: {}", address);
		Address ip = repository.save(address);
		if (ip == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ip.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(ip), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{ip}", method=RequestMethod.PUT)
	public HttpEntity<AddressResource> updateAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip, @RequestBody @NotNull Address address) {
		Preconditions.checkArgument(subnetId.equals(address.getSubnetId()));
		Preconditions.checkArgument(ip.equals(address.getIp()));
		LOGGER.info("update address: {}", address);
		Address addr = repository.save(address);
		if (addr == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		addr.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(addr), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{addressId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long addressId) {
		LOGGER.info("delete address with id: {} (infra id: {}, subnet id: {})", addressId, infraId, subnetId);
		this.repository.deleteBySubnetIdAndIp(subnetId, addressId);
	}
}
