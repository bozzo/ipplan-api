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

import java.util.Date;

import org.bozzo.ipplan.domain.model.AddressPlan;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author boris
 *
 */
@Getter
public class AddressPlanResource extends ResourceSupport {
	
	private final Integer infraId;
	private final Long planId;
	private final Date lastModifed;
	private final String description;
	private final String name;
	

	/**
	 * @param ip
	 * @param infraId
	 * @param planId
	 * @param lastModifed
	 * @param description
	 * @param name
	 */
	@JsonCreator
	public AddressPlanResource(Integer infraId, Long planId, Date lastModifed, String description,
			String name) {
		super();
		this.infraId = infraId;
		this.planId = planId;
		this.lastModifed = lastModifed;
		this.description = description;
		this.name = name;
	}

	public AddressPlanResource(AddressPlan plan) {
		this(
				plan.getInfraId(),
				plan.getId(),
				plan.getLastModifed(),
				plan.getDescription(),
				plan.getName()
		);
	}
}
