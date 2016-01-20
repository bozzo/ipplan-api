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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author boris
 *
 */
@Entity
@Table(name="ipaddr")
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4069178642893810829L;

	@Id
	@Column(name="ipaddr", nullable=false)
	private Long ip;
	
	@Transient
	private Integer infraId;

	@Column(name="baseindex", nullable=false)
	private Long subnetId;

	@Column(name="lastmod", nullable=false)
	private Date lastModifed;

	@Column(name="lastpol")
	private Date lastPol;
	
	@Column(name="macaddr")
	private String mac;

	@Column(name="descrip")
	private String description;

	@Column(name="hname")
	private String name;

	@Column(name="telno")
	private String phone;

	@Column(name="userinf")
	private String userInfo;

	@Column(name="userid")
	private String userId;
	
	@Column(name="location")
	private String location;
	
	@Transient
	private boolean free;

	/**
	 * 
	 */
	public Address() {
		super();
		this.free = false;
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
	 * @return the subnetId
	 */
	public Long getSubnetId() {
		return subnetId;
	}

	/**
	 * @param subnetId the subnetId to set
	 */
	public void setSubnetId(Long subnetId) {
		this.subnetId = subnetId;
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
	 * @return the lastPol
	 */
	public Date getLastPol() {
		return lastPol;
	}

	/**
	 * @param lastPol the lastPol to set
	 */
	public void setLastPol(Date lastPol) {
		this.lastPol = lastPol;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the userInfo
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [ip=").append(ip).append(", subnetId=").append(subnetId).append(", lastModifed=")
				.append(lastModifed).append(", lastPol=").append(lastPol).append(", mac=").append(mac)
				.append(", description=").append(description).append(", name=").append(name).append(", phone=")
				.append(phone).append(", userInfo=").append(userInfo).append(", userId=").append(userId)
				.append(", location=").append(location).append(", free=").append(free).append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((subnetId == null) ? 0 : subnetId.hashCode());
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
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (subnetId == null) {
			if (other.subnetId != null)
				return false;
		} else if (!subnetId.equals(other.subnetId))
			return false;
		return true;
	}

	
}
