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
import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.tools.Netmask;
import org.bozzo.ipplan.web.json.StreamSerializer;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

/**
 * @author boris
 *
 */
@Getter @Setter
public class RangeResource extends ResourceSupport {

	private final Long rangeId;
	private final Long ip;
	private final Long size;
	private final String description;
	private final Long zoneId;
	private final Integer infraId;
	private final Integer netmask;
	
	@JsonSerialize(using=StreamSerializer.class)
	private Stream<SubnetResource> subnets;
	
	/**
	 * @param id
	 * @param ip
	 * @param size
	 * @param description
	 * @param zoneId
	 * @param infraId
	 */
	@JsonCreator
	public RangeResource(@JsonProperty("id") Long id, @JsonProperty Long ip, @JsonProperty Long size, @JsonProperty Integer netmask, @JsonProperty String description, @JsonProperty Long zoneId, @JsonProperty Integer infraId, @JsonProperty Iterable<Subnet> subnets) {
		super();
		this.rangeId = id;
		this.ip = ip;
		this.size = size;
		this.netmask = netmask;
		this.description = description;
		this.zoneId = zoneId;
		this.infraId = infraId;
		if (subnets != null) {
			this.setSubnets(StreamSupport.stream(subnets.spliterator(), true).map(new ToSubnetResourceFunction()));
		}
	}
	
	public RangeResource(Range range) {
		this(range.getId(),range.getIp(),range.getSize(),Netmask.fromNumberHosts(range.getSize()),range.getDescription(),range.getZoneId(),range.getInfraId(), range.getSubnets());
	}

	/**
	 * @return the id
	 */
	@JsonProperty("id")
	public Long getRangeId() {
		return rangeId;
	}
}
