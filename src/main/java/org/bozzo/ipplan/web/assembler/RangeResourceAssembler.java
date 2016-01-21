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

import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.ui.RangeResource;
import org.bozzo.ipplan.tools.IpAddress;
import org.bozzo.ipplan.web.InfrastructureController;
import org.bozzo.ipplan.web.RangeController;
import org.bozzo.ipplan.web.SubnetController;
import org.bozzo.ipplan.web.ZoneController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author boris
 *
 */
@Component
public class RangeResourceAssembler extends ResourceAssemblerSupport<Range, RangeResource> {

	public RangeResourceAssembler() {
		super(RangeController.class, RangeResource.class);
	}

	public RangeResourceAssembler(Class<?> controllerClass, Class<RangeResource> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public RangeResource toResource(Range entity) {
		RangeResource range = new RangeResource(entity);
		range.add(linkTo(methodOn(ZoneController.class).getZone(range.getInfraId(), range.getZoneId())).withRel("zone"));
		range.add(linkTo(methodOn(InfrastructureController.class).getInfrastructure(range.getInfraId())).withRel("infra"));
		range.add(linkTo(methodOn(SubnetController.class).getSubnets(IpAddress.toString(range.getIp()), range.getSize(), range.getInfraId(), null, null)).withRel("subnets"));
		range.add(linkTo(methodOn(RangeController.class).getRange(range.getInfraId(), range.getZoneId(),range.getRangeId())).withSelfRel());
		return range;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<RangeResource> toResources(Iterable<? extends Range> entities) {
		List<RangeResource> resources = new ArrayList<>();
        for(Range range : entities) {
            resources.add(this.toResource(range));
        }
        return resources;
	}
	
}
