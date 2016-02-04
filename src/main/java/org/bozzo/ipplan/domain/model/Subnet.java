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
package org.bozzo.ipplan.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.hateoas.Identifiable;

/**
 * @author boris
 *
 */
@Entity
@Table(name="base")
public class Subnet implements Serializable, Identifiable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3938246853021759159L;

	@Id
	@GeneratedValue
	@Column(name="baseindex", nullable=false)
	private Long id;

	@Column(name="customer", nullable=false)
	private Integer infraId;

	@Column(name="baseaddr", nullable=false)
	private Long ip;
	
	@Column(name="subnetsize", nullable=false)
	private Long size;

	@Column(name="descrip", nullable=false)
	private String description;

	@Column(name="admingrp", nullable=false)
	private String group;

	@Column(name="lastmod", nullable=false)
	private Date lastModifed;

	@Column(name="userid", nullable=false)
	private String userId;
	
	@Column(name="baseopt", nullable=false)
	private Long optionId;

	@Column(name="swipmod")
	private Date swipMod;
	
	@Transient
	private Iterable<Address> addresses;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the infraId
	 */
	public Integer getInfraId() {
		return infraId;
	}

	/**
	 * @param infraId the infraId to set
	 */
	public void setInfraId(Integer infraId) {
		this.infraId = infraId;
	}

	/**
	 * @return the ip
	 */
	public Long getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(Long ip) {
		this.ip = ip;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the lastModifed
	 */
	public Date getLastModifed() {
		return lastModifed;
	}

	/**
	 * @param lastModifed the lastModifed to set
	 */
	public void setLastModifed(Date lastModifed) {
		this.lastModifed = lastModifed;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the optionId
	 */
	public Long getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId the optionId to set
	 */
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	/**
	 * @return the swipMod
	 */
	public Date getSwipMod() {
		return swipMod;
	}

	/**
	 * @param swipMod the swipMod to set
	 */
	public void setSwipMod(Date swipMod) {
		this.swipMod = swipMod;
	}

	/**
	 * @return the addresses
	 */
	public Iterable<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(Iterable<Address> addresses) {
		this.addresses = addresses;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subnet [id=").append(id).append(", infraId=").append(infraId).append(", ip=").append(ip)
				.append(", size=").append(size).append(", description=").append(description).append(", group=")
				.append(group).append(", lastModifed=").append(lastModifed).append(", userId=").append(userId)
				.append(", optionId=").append(optionId).append(", swipMod=").append(swipMod).append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((infraId == null) ? 0 : infraId.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Subnet))
			return false;
		Subnet other = (Subnet) obj;
		if (infraId == null) {
			if (other.infraId != null)
				return false;
		} else if (!infraId.equals(other.infraId))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}
}
