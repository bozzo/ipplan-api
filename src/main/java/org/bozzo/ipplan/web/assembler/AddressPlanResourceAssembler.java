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

import org.bozzo.ipplan.domain.model.AddressPlan;
import org.bozzo.ipplan.domain.model.ui.AddressPlanResource;
import org.bozzo.ipplan.web.AddressPlanController;
import org.bozzo.ipplan.web.InfrastructureController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author boris
 *
 */
@Component
public class AddressPlanResourceAssembler extends ResourceAssemblerSupport<AddressPlan, AddressPlanResource> {

	public AddressPlanResourceAssembler() {
		super(AddressPlanController.class, AddressPlanResource.class);
	}

	@Override
	public AddressPlanResource toResource(AddressPlan entity) {
		AddressPlanResource addressPlan = new AddressPlanResource(entity);
		addressPlan.add(linkTo(methodOn(AddressPlanController.class).getAddressPlan(addressPlan.getInfraId(), addressPlan.getPlanId())).withSelfRel());
		addressPlan.add(linkTo(methodOn(InfrastructureController.class).getInfrastructure(addressPlan.getInfraId(), null)).withRel("infra"));
		return addressPlan;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<AddressPlanResource> toResources(Iterable<? extends AddressPlan> entities) {
		List<AddressPlanResource> resources = new ArrayList<>();
        for(AddressPlan addressPlan : entities) {
            resources.add(this.toResource(addressPlan));
        }
        return resources;
	}
	
}
