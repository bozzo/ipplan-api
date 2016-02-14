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
@Table(name="addressplan")
public class AddressPlan implements Serializable, Identifiable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 220534349660330045L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false)
	private Long id;

	@Column(name="customer", nullable=false)
	private Integer infraId;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="descrip", nullable=false)
	private String description;

	@Column(name="lastmod", nullable=false)
	private Date lastModifed;
	
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
	 * @return the code
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param code the code to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the subnets
	 */
	public Iterable<Subnet> getSubnets() {
		return subnets;
	}

	/**
	 * @param subnets the subnets to set
	 */
	public void setSubnets(Iterable<Subnet> subnets) {
		this.subnets = subnets;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddressPlan [id=").append(id).append(", infraId=").append(infraId).append(", name=")
				.append(name).append(", description=").append(description).append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((infraId == null) ? 0 : infraId.hashCode());
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
		if (!(obj instanceof AddressPlan))
			return false;
		AddressPlan other = (AddressPlan) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (infraId == null) {
			if (other.infraId != null)
				return false;
		} else if (!infraId.equals(other.infraId))
			return false;
		return true;
	}
}
