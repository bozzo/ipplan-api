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
package org.bozzo.ipplan.domain.model.ui;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bozzo.ipplan.domain.functions.ToSubnetResourceFunction;
import org.bozzo.ipplan.domain.functions.ToZoneResourceFunction;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.web.json.StreamSerializer;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author boris
 *
 */
public class InfrastructureResource extends ResourceSupport {

	private final Integer id;
	private final String description;
	private final String crm;
	private final String group;
	
	@JsonSerialize(using=StreamSerializer.class)
	private Stream<ZoneResource> zones;
	
	@JsonSerialize(using=StreamSerializer.class)
	private Stream<SubnetResource> subnets;
	
	/**
	 * @param id
	 * @param description
	 * @param crm
	 * @param group
	 */
	@JsonCreator
	public InfrastructureResource(@JsonProperty("id") Integer id, @JsonProperty String description, @JsonProperty String crm, @JsonProperty String group, @JsonProperty Iterable<Zone> zones, @JsonProperty Iterable<Subnet> subnets) {
		this.id = id;
		this.description = description;
		this.crm = crm;
		this.group = group;
		if (zones != null) {
			this.setZones(StreamSupport.stream(zones.spliterator(), true).map(new ToZoneResourceFunction()));
		}
		if (subnets != null) {
			this.setSubnets(StreamSupport.stream(subnets.spliterator(), true).map(new ToSubnetResourceFunction()));
		}
	}

	public InfrastructureResource(Infrastructure infra) {
		this(infra.getId(),infra.getDescription(),infra.getCrm(),infra.getGroup(), infra.getZones(), infra.getSubnets());
	}

	/**
	 * @return the id
	 */
	@JsonProperty("id")
	public Integer getInfraId() {
		return this.id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the crm
	 */
	public String getCrm() {
		return crm;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @return the zones
	 */
	public Stream<ZoneResource> getZones() {
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(Stream<ZoneResource> zones) {
		this.zones = zones;
	}

	/**
	 * @return the subnets
	 */
	public Stream<SubnetResource> getSubnets() {
		return subnets;
	}

	/**
	 * @param subnets the subnets to set
	 */
	public void setSubnets(Stream<SubnetResource> subnets) {
		this.subnets = subnets;
	}
}