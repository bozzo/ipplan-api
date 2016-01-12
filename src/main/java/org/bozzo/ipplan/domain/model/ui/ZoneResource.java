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
	
	/**
	 * @param id
	 * @param infraId
	 * @param ip
	 * @param description
	 */
	@JsonCreator
	public ZoneResource(@JsonProperty("id") Long id, @JsonProperty Integer infraId, @JsonProperty Long ip, @JsonProperty String description) {
		super();
		this.id = id;
		this.infraId = infraId;
		this.ip = ip;
		this.description = description;
	}

	public ZoneResource(Zone zone) {
		this(zone.getId(),zone.getInfraId(),zone.getIp(),zone.getDescription());
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
}
