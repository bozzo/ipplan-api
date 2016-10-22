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
@Table(name="area")
@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of={"ip","infraId"})
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
}
