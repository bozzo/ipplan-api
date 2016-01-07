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

import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.web.InfrastructureController;
import org.bozzo.ipplan.web.SubnetController;
import org.bozzo.ipplan.web.ZoneController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author boris
 *
 */
@Component
public class InfrastructureResourceAssembler extends ResourceAssemblerSupport<Infrastructure, InfrastructureResource> {

	public InfrastructureResourceAssembler() {
		super(InfrastructureController.class, InfrastructureResource.class);
	}

	public InfrastructureResourceAssembler(Class<?> controllerClass, Class<InfrastructureResource> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InfrastructureResource toResource(Infrastructure entity) {
		InfrastructureResource infra = new InfrastructureResource(entity);
		infra.add(linkTo(methodOn(InfrastructureController.class).getInfrastructure(infra.getInfraId())).withSelfRel());
		infra.add(linkTo(methodOn(ZoneController.class).getZones(infra.getInfraId(), null, null)).withRel("zones"));
		infra.add(linkTo(methodOn(SubnetController.class).getSubnets(null, null, infra.getInfraId(), null, null)).withRel("subnets"));
		return infra;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<InfrastructureResource> toResources(Iterable<? extends Infrastructure> entities) {
		List<InfrastructureResource> resources = new ArrayList<>();
        for(Infrastructure infra : entities) {
            resources.add(this.toResource(infra));
        }
        return resources;
	}

	public static Link link(Integer infraId) {
		return linkTo(methodOn(InfrastructureController.class).getInfrastructure(infraId)).withRel("infra");
	}
	
}
