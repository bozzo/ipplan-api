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

import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.tools.Netmask;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author boris
 *
 */
public class SubnetResource extends ResourceSupport {

	private final Long id;
	private final Integer infraId;
	private final Long ip;
	private final Long size;
	private final Integer netmask;
	private final String description;
	private final String group;
	private final Date lastModifed;
	private final String userId;
	private final Long optionId;
	private final Date swipMod;
	
	/**
	 * @param id
	 * @param infraId
	 * @param ip
	 * @param size
	 * @param description
	 * @param group
	 * @param lastModifed
	 * @param userId
	 * @param optionId
	 * @param swipMod
	 */
	@JsonCreator
	public SubnetResource( @JsonProperty("id") Long id, @JsonProperty Integer infraId, @JsonProperty Long ip, @JsonProperty Integer netmask, @JsonProperty Long size, @JsonProperty String description, @JsonProperty String group,
			@JsonProperty Date lastModifed, @JsonProperty String userId, @JsonProperty Long optionId, @JsonProperty Date swipMod) {
		this.id = id;
		this.infraId = infraId;
		this.ip = ip;
		this.size = size;
		this.netmask = netmask;
		this.description = description;
		this.group = group;
		this.lastModifed = lastModifed;
		this.userId = userId;
		this.optionId = optionId;
		this.swipMod = swipMod;
	}
	
	public SubnetResource( Subnet subnet) {
		this.id = subnet.getId();
		this.infraId = subnet.getInfraId();
		this.ip = subnet.getIp();
		this.size = subnet.getSize();
		this.netmask = Netmask.fromNumberHosts(subnet.getSize().intValue());
		this.description = subnet.getDescription();
		this.group = subnet.getGroup();
		this.lastModifed = subnet.getLastModifed();
		this.userId = subnet.getUserId();
		this.optionId = subnet.getOptionId();
		this.swipMod = subnet.getSwipMod();
	}

	/**
	 * @return the id
	 */
	@JsonProperty("id")
	public Long getSubnetId() {
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
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @return the netmask
	 */
	public Integer getNetmask() {
		return netmask;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @return the lastModifed
	 */
	public Date getLastModifed() {
		return lastModifed;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the optionId
	 */
	public Long getOptionId() {
		return optionId;
	}

	/**
	 * @return the swipMod
	 */
	public Date getSwipMod() {
		return swipMod;
	}
}
