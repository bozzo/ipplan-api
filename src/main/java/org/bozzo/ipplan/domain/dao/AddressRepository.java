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

import java.util.List;

import javax.transaction.Transactional;

import org.bozzo.ipplan.domain.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author boris
 *
 */
@Repository
@Transactional
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN 'true' ELSE 'false' END FROM Address a WHERE a.subnetId = :subnetId")
	public Boolean existsBySubnet(@Param("subnetId") Long subnetId);

	@Query("SELECT a.ip FROM Address a WHERE a.subnetId=:subnetId")
	public List<Long> findAllIpBySubnetId(@Param("subnetId") Long subnetId);

	public Page<Address> findBySubnetId(Long subnetId, Pageable pageable);

	public Address findBySubnetIdAndIp(Long subnetId, Long ip);

	public void deleteBySubnetIdAndIp(Long subnetId, Long ip);

	public void deleteBySubnetId(Long subnetId);

}
