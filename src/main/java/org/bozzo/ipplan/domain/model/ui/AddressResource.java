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

/**
 * @author boris
 *
 */
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
			String name, String phone, String userInfo, String userId, String location) {
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
	}

	public AddressResource(Address address) {
		super();
		this.ip = address.getIp();
		this.infraId = address.getInfraId();
		this.subnetId = address.getSubnetId();
		this.lastModifed = address.getLastModifed();
		this.lastPol = address.getLastPol();
		this.mac = address.getMac();
		this.description = address.getDescription();
		this.name = address.getName();
		this.phone = address.getPhone();
		this.userInfo = address.getUserInfo();
		this.userId = address.getUserId();
		this.location = address.getLocation();
	}

	/**
	 * @return the ip
	 */
	public Long getIp() {
		return ip;
	}
	/**
	 * @return the infraId
	 */
	public Integer getInfraId() {
		return infraId;
	}

	/**
	 * @return the subnetId
	 */
	public Long getSubnetId() {
		return subnetId;
	}
	/**
	 * @return the lastModifed
	 */
	public Date getLastModifed() {
		return lastModifed;
	}
	/**
	 * @return the lastPol
	 */
	public Date getLastPol() {
		return lastPol;
	}
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @return the userInfo
	 */
	public String getUserInfo() {
		return userInfo;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
}