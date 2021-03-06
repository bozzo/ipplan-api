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

import org.bozzo.ipplan.domain.model.Address;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author boris
 *
 */
@Getter
public class AddressResource extends ResourceSupport {
	
	private final Long ip;
	private final Integer infraId;
	private final Long subnetId;
	private final Date lastModifed;
	private final Date lastPol;
	private final String mac;
	private final String description;
	private final String name;
	private final String phone;
	private final String userInfo;
	private final String userId;
	private final String location;
	private final boolean free;
	/**
	 * @param ip
	 * @param subnetId
	 * @param lastModifed
	 * @param lastPol
	 * @param mac
	 * @param description
	 * @param name
	 * @param phone
	 * @param userInfo
	 * @param userId
	 * @param location
	 */
	@JsonCreator
	public AddressResource(Long ip, Integer infraId, Long subnetId, Date lastModifed, Date lastPol, String mac, String description,
			String name, String phone, String userInfo, String userId, String location, boolean free) {
		super();
		this.ip = ip;
		this.infraId = infraId;
		this.subnetId = subnetId;
		this.lastModifed = lastModifed;
		this.lastPol = lastPol;
		this.mac = mac;
		this.description = description;
		this.name = name;
		this.phone = phone;
		this.userInfo = userInfo;
		this.userId = userId;
		this.location = location;
		this.free = free;
	}

	public AddressResource(Address address) {
		this(
				address.getIp(),
				address.getInfraId(),
				address.getSubnetId(),
				address.getLastModifed(),
				address.getLastPol(),
				address.getMac(),
				address.getDescription(),
				address.getName(),
				address.getPhone(),
				address.getUserInfo(),
				address.getUserId(),
				address.getLocation(),
				address.isFree()
		);
	}
}
