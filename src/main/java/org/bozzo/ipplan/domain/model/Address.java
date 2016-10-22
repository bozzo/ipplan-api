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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author boris
 *
 */
@Entity
@Table(name="ipaddr")
@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of={"ip","subnetId"})
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
	private boolean free = false;
}
