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
package org.bozzo.ipplan.domain.dao;

import javax.transaction.Transactional;

import org.bozzo.ipplan.domain.model.AddressPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author boris
 *
 */
@Repository
@Transactional
public interface AddressPlanRepository extends PagingAndSortingRepository<AddressPlan, Long> {

	public Page<AddressPlan> findAllByInfraId(Integer infraId, Pageable pageable);

	public AddressPlan findByInfraIdAndId(Integer infraId, Long id);

	public AddressPlan findByInfraIdAndName(Integer infraId, String name);

	public void deleteByInfraIdAndId(Integer infraId, Long id);

}
