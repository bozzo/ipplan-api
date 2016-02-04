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

import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.ui.SubnetResource;
import org.bozzo.ipplan.web.AddressController;
import org.bozzo.ipplan.web.InfrastructureController;
import org.bozzo.ipplan.web.SubnetController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author boris
 *
 */
@Component
public class SubnetResourceAssembler extends ResourceAssemblerSupport<Subnet, SubnetResource> {

	public SubnetResourceAssembler() {
		super(SubnetController.class, SubnetResource.class);
	}

	@Override
	public SubnetResource toResource(Subnet entity) {
		SubnetResource subnet = new SubnetResource(entity);
		subnet.add(linkTo(methodOn(SubnetController.class).getSubnet(subnet.getInfraId(), subnet.getSubnetId(), null)).withSelfRel());
		subnet.add(linkTo(methodOn(InfrastructureController.class).getInfrastructure(subnet.getInfraId())).withRel("infra"));
		subnet.add(linkTo(methodOn(AddressController.class).getAddresses(null,subnet.getInfraId(), subnet.getSubnetId(), null, null)).withRel("addresses"));
		return subnet;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<SubnetResource> toResources(Iterable<? extends Subnet> entities) {
		List<SubnetResource> resources = new ArrayList<>();
        for(Subnet subnet : entities) {
            resources.add(this.toResource(subnet));
        }
        return resources;
	}

	public static Link link(Integer infraId, Long subnetId) {
		return linkTo(methodOn(SubnetController.class).getSubnet(infraId, subnetId, null)).withRel("subnet");
	}
	
}
