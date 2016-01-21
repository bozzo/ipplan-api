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

import org.bozzo.ipplan.domain.Mode;
import org.bozzo.ipplan.domain.dao.AddressRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Address;
import org.bozzo.ipplan.domain.model.ApiError;
import org.bozzo.ipplan.domain.model.ui.AddressResource;
import org.bozzo.ipplan.domain.service.AddressService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	private AddressService service;
	
	@Autowired
	private AddressRepository repository;
	
	@Autowired
	private AddressResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	public ModelAndView getAddressesView(@RequestParam(required = false) Mode mode, @PathVariable Integer infraId, @PathVariable Long subnetId, Pageable pageable, PagedResourcesAssembler<Address> pagedAssembler) {
		PagedResources<AddressResource> addresses = this.getAddresses(mode, infraId, subnetId, pageable, pagedAssembler);
		ModelAndView view = new ModelAndView("addresses");
		view.addObject("pages", addresses);
		return view;
	}

	@RequestMapping(method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public PagedResources<AddressResource> getAddresses(@RequestParam(required = false) Mode mode, @PathVariable Integer infraId, @PathVariable Long subnetId, Pageable pageable, PagedResourcesAssembler<Address> pagedAssembler) {
		Page<Address> addresses;
		if (Mode.Free.equals(mode)) {
			addresses = this.service.findFreeBySubnetId(subnetId, pageable);
		} else {
			addresses = this.repository.findBySubnetId(subnetId, pageable);
		}
		for (Address address : addresses) {
			address.setInfraId(infraId);
		}
		return pagedAssembler.toResource(addresses, assembler);
	}

	@RequestMapping(value = "/free", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<AddressResource> getFreeAddress(@PathVariable Integer infraId, @PathVariable Long subnetId) {
		Address address = this.service.findFreeAddressBySubnetId(subnetId);
		if (address == null) {
			throw new ApiException(ApiError.SubnetFull);
		}
		address.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(address), HttpStatus.OK);
	}

	@RequestMapping(value = "/{ip}", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	public ModelAndView getAddressView(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip) {
		HttpEntity<AddressResource> address = this.getAddress(infraId, subnetId, ip);
		ModelAndView view = new ModelAndView("address");
		view.addObject("id", ip);
		view.addObject("object", address.getBody());
		return view;
	}

	@RequestMapping(value = "/{ip}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<AddressResource> getAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip) {
		Address address = this.repository.findBySubnetIdAndIp(subnetId, ip);
		if (address == null) {
			throw new ApiException(ApiError.IPNotFound);
		}
		address.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(address), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<AddressResource> addAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @RequestBody @NotNull Address address) {
		Preconditions.checkArgument(infraId.equals(address.getInfraId()));
		Preconditions.checkArgument(subnetId.equals(address.getSubnetId()));
		LOGGER.info("add new address: {}", address);
		if (address.getIp() == null) { 
			Address freeAddress = this.service.findFreeAddressBySubnetId(subnetId);
			if (freeAddress == null) {
				throw new ApiException(ApiError.SubnetFull);
			}
			address.setIp(freeAddress.getIp());
		} else if (this.repository.exists(address.getIp())) {
			throw new ApiException(ApiError.IPConflict);
		}
		Address ip = repository.save(address);
		ip.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(ip), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{ip}", method=RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<AddressResource> updateAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long ip, @RequestBody @NotNull Address address) {
		Preconditions.checkArgument(subnetId.equals(address.getSubnetId()));
		Preconditions.checkArgument(ip.equals(address.getIp()));
		LOGGER.info("update address: {}", address);
		Address addr = repository.save(address);
		addr.setInfraId(infraId);
		return new ResponseEntity<>(assembler.toResource(addr), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{addressId}", method=RequestMethod.DELETE)
	public @ResponseStatus(HttpStatus.NO_CONTENT) void deleteAddress(@PathVariable Integer infraId, @PathVariable Long subnetId, @PathVariable Long addressId) {
		LOGGER.info("delete address with id: {} (infra id: {}, subnet id: {})", addressId, infraId, subnetId);
		this.repository.deleteBySubnetIdAndIp(subnetId, addressId);
	}
}
