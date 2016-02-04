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

import org.bozzo.ipplan.domain.functions.ToRangeResourceFunction;
import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.Zone;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author boris
 *
 */
public class ZoneResource extends ResourceSupport {

	private final Long id;
	private final Integer infraId;
	private final Long ip;
	private final String description;
	private Stream<RangeResource> ranges;
	
	/**
	 * @param id
	 * @param infraId
	 * @param ip
	 * @param description
	 */
	@JsonCreator
	public ZoneResource(@JsonProperty("id") Long id, @JsonProperty Integer infraId, @JsonProperty Long ip, @JsonProperty String description, @JsonProperty Iterable<Range> ranges) {
		super();
		this.id = id;
		this.infraId = infraId;
		this.ip = ip;
		this.description = description;
		if (ranges != null) {
			this.setRanges(StreamSupport.stream(ranges.spliterator(), true).map(new ToRangeResourceFunction()));
		}
	}

	public ZoneResource(Zone zone) {
		this(zone.getId(),zone.getInfraId(),zone.getIp(),zone.getDescription(), zone.getRanges());
	}

	/**
	 * @return the id
	 */
	@JsonProperty("id") 
	public Long getZoneId() {
		return id;
	}

	/**
	 * @return the infraId
	 */
	public Integer getInfraId() {
		return infraId;
	}

	/**
	 * @return the ip
	 */
	public Long getIp() {
		return ip;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the ranges
	 */
	public Stream<RangeResource> getRanges() {
		return ranges;
	}

	/**
	 * @param ranges the ranges to set
	 */
	public void setRanges(Stream<RangeResource> ranges) {
		this.ranges = ranges;
	}
}
