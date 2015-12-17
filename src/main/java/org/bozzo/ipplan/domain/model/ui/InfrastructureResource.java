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

import org.bozzo.ipplan.domain.model.Infrastructure;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author boris
 *
 */
public class InfrastructureResource extends ResourceSupport {

	private final Integer id;
	private final String description;
	private final String crm;
	private final String group;
	
	/**
	 * @param id
	 * @param description
	 * @param crm
	 * @param group
	 */
	@JsonCreator
	public InfrastructureResource(@JsonProperty("id") Integer id, @JsonProperty String description, @JsonProperty String crm, @JsonProperty String group) {
		this.id = id;
		this.description = description;
		this.crm = crm;
		this.group = group;
	}

	public InfrastructureResource(Infrastructure infra) {
		this.id = infra.getId();
		this.description = infra.getDescription();
		this.crm = infra.getCrm();
		this.group = infra.getGroup();
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
}