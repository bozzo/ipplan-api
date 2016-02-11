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

import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.ZoneResource;
import org.bozzo.ipplan.web.InfrastructureController;
import org.bozzo.ipplan.web.RangeController;
import org.bozzo.ipplan.web.ZoneController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author boris
 *
 */
@Component
public class ZoneResourceAssembler extends ResourceAssemblerSupport<Zone, ZoneResource> {

	public ZoneResourceAssembler() {
		super(ZoneController.class, ZoneResource.class);
	}

	@Override
	public ZoneResource toResource(Zone entity) {
		ZoneResource zone = new ZoneResource(entity);
		zone.add(linkTo(methodOn(ZoneController.class).getZone(zone.getInfraId(), zone.getZoneId(), null)).withSelfRel());
		zone.add(linkTo(methodOn(InfrastructureController.class).getInfrastructure(zone.getInfraId(), null)).withRel("infra"));
		zone.add(linkTo(methodOn(RangeController.class).getRanges(zone.getInfraId(), zone.getZoneId(), null, null)).withRel("ranges"));
		return zone;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<ZoneResource> toResources(Iterable<? extends Zone> entities) {
		List<ZoneResource> resources = new ArrayList<>();
        for(Zone zone : entities) {
            resources.add(this.toResource(zone));
        }
        return resources;
	}

	public static Link link(Integer infraId, Long zoneId) {
		return linkTo(methodOn(ZoneController.class).getZone(infraId, zoneId, null)).withRel("zone");
	}
	
}
