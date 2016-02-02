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
package org.bozzo.ipplan.web.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.bozzo.ipplan.domain.model.Address;
import org.bozzo.ipplan.domain.model.ui.AddressResource;
import org.bozzo.ipplan.web.AddressController;
import org.bozzo.ipplan.web.InfrastructureController;
import org.bozzo.ipplan.web.SubnetController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author boris
 *
 */
@Component
public class AddressResourceAssembler extends ResourceAssemblerSupport<Address, AddressResource> {

	public AddressResourceAssembler() {
		super(AddressController.class, AddressResource.class);
	}

	public AddressResourceAssembler(Class<?> controllerClass, Class<AddressResource> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public AddressResource toResource(Address entity) {
		AddressResource address = new AddressResource(entity);
		address.add(linkTo(methodOn(SubnetController.class).getSubnet(address.getInfraId(), address.getSubnetId(), null)).withRel("subnet"));
		address.add(linkTo(methodOn(InfrastructureController.class).getInfrastructure(address.getInfraId())).withRel("infra"));
		address.add(linkTo(methodOn(AddressController.class).getAddress(address.getInfraId(), address.getSubnetId(), address.getIp())).withSelfRel());
		return address;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<AddressResource> toResources(Iterable<? extends Address> entities) {
		List<AddressResource> resources = new ArrayList<>();
        for(Address address : entities) {
            resources.add(this.toResource(address));
        }
        return resources;
	}
	
}
