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

import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.tools.Netmask;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author boris
 *
 */
public class RangeResource extends ResourceSupport {

	private final Long id;
	private final Long ip;
	private final Long size;
	private final String description;
	private final Long zoneId;
	private final Integer infraId;
	private final Integer netmask;
	
	/**
	 * @param id
	 * @param ip
	 * @param size
	 * @param description
	 * @param zoneId
	 * @param infraId
	 */
	@JsonCreator
	public RangeResource(@JsonProperty("id") Long id, @JsonProperty Long ip, @JsonProperty Long size, @JsonProperty Integer netmask, @JsonProperty String description, @JsonProperty Long zoneId, @JsonProperty Integer infraId) {
		super();
		this.id = id;
		this.ip = ip;
		this.size = size;
		this.netmask = netmask;
		this.description = description;
		this.zoneId = zoneId;
		this.infraId = infraId;
	}
	
	public RangeResource(Range range) {
		this(range.getId(),range.getIp(),range.getSize(),Netmask.fromNumberHosts(range.getSize()),range.getDescription(),range.getZoneId(),range.getInfraId());
	}

	/**
	 * @return the id
	 */
	@JsonProperty("id")
	public Long getRangeId() {
		return id;
	}

	/**
	 * @return the ip
	 */
	public Long getIp() {
		return ip;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the zoneId
	 */
	public Long getZoneId() {
		return zoneId;
	}

	/**
	 * @return the infraId
	 */
	public Integer getInfraId() {
		return infraId;
	}

	/**
	 * @return the netmask
	 */
	public Integer getNetmask() {
		return netmask;
	}
	
}
