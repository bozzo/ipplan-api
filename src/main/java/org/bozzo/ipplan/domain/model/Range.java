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
@Table(name="netrange")
public class Range implements Serializable, Identifiable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1566720591130862498L;

	@Id
	@GeneratedValue
	@Column(name="rangeindex", nullable=false)
	private Long id;
	
	@Column(name="rangeaddr", nullable=false)
	private Long ip;
	
	@Column(name="rangesize", nullable=false)
	private Long size;

	@Column(name="descrip", nullable=false)
	private String description;

	@Column(name="areaindex", nullable=false)
	private Long zoneId;

	@Column(name="customer", nullable=false)
	private Integer infraId;
	
	@Transient
	private Iterable<Subnet> subnets;

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
	 * @return the zoneId
	 */
	public Long getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
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
	 * @return the subnet
	 */
	public Iterable<Subnet> getSubnets() {
		return subnets;
	}

	/**
	 * @param subnet the subnet to set
	 */
	public void setSubnet(Iterable<Subnet> subnets) {
		this.subnets = subnets;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Range [id=").append(id).append(", ip=").append(ip).append(", size=").append(size)
				.append(", description=").append(description).append(", zoneId=").append(zoneId).append(", infraId=")
				.append(infraId).append("]");
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
		result = prime * result + ((zoneId == null) ? 0 : zoneId.hashCode());
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
		if (!(obj instanceof Range))
			return false;
		Range other = (Range) obj;
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
		if (zoneId == null) {
			if (other.zoneId != null)
				return false;
		} else if (!zoneId.equals(other.zoneId))
			return false;
		return true;
	}

}
