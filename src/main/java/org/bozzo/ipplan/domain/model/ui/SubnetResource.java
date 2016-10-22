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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bozzo.ipplan.domain.functions.ToAddressResourceFunction;
import org.bozzo.ipplan.domain.model.Address;
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
public class SubnetResource extends ResourceSupport {

	private final Long subnetId;
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
	
	@JsonSerialize(using=StreamSerializer.class)
	private Stream<AddressResource> addresses;
	
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
			@JsonProperty Date lastModifed, @JsonProperty String userId, @JsonProperty Long optionId, @JsonProperty Date swipMod, @JsonProperty Iterable<Address> addresses) {
		this.subnetId = id;
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
		if (addresses != null) {
			this.setAddresses(StreamSupport.stream(addresses.spliterator(), true).map(new ToAddressResourceFunction()));
		}
	}
	
	public SubnetResource( Subnet subnet) {
		this(
				subnet.getId(),
				subnet.getInfraId(),
				subnet.getIp(),
				Netmask.fromNumberHosts(subnet.getSize()),
				subnet.getSize(),
				subnet.getDescription(),
				subnet.getGroup(),
				subnet.getLastModifed(),
				subnet.getUserId(),
				subnet.getOptionId(),
				subnet.getSwipMod(),
				subnet.getAddresses()
			);
	}

	/**
	 * @return the id
	 */
	@JsonProperty("id")
	public Long getSubnetId() {
		return subnetId;
	}
}
