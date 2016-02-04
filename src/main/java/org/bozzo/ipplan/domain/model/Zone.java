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
@Table(name="area")
public class Zone implements Serializable, Identifiable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 868164004579979904L;

	@Id
	@GeneratedValue
	@Column(name="areaindex", nullable=false)
	private Long id;

	@Column(name="customer", nullable=false)
	private Integer infraId;

	@Column(name="areaaddr", nullable=false)
	private Long ip;

	@Column(name="descrip", nullable=false)
	private String description;
	
	@Transient
	private Iterable<Range> ranges;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(Long ip) {
		this.ip = ip;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the ranges
	 */
	public Iterable<Range> getRanges() {
		return ranges;
	}

	/**
	 * @param ranges the ranges to set
	 */
	public void setRanges(Iterable<Range> ranges) {
		this.ranges = ranges;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Zone [id=").append(id).append(", infraId=").append(infraId).append(", ip=").append(ip)
				.append(", description=").append(description).append("]");
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
		if (!(obj instanceof Zone))
			return false;
		Zone other = (Zone) obj;
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
		return true;
	}
}
