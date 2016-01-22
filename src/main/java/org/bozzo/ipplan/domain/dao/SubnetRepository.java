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

import org.bozzo.ipplan.domain.model.Subnet;
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
public interface SubnetRepository extends PagingAndSortingRepository<Subnet, Long> {

	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN 'true' ELSE 'false' END FROM Subnet s WHERE s.id = :subnetId AND s.ip < :ip AND (s.ip + s.size - 1) > :ip")
	public Boolean existsInSubnet(@Param("subnetId") Long subnetId, @Param("ip") Long ip);

	public Page<Subnet> findAllByInfraId(Integer infraId, Pageable pageable);

	@Query("SELECT s FROM Subnet s WHERE s.infraId = :infraId AND s.ip <= :ip AND (s.ip + s.size) > :ip")
	public Page<Subnet> findAllByInfraIdAndIp(@Param("infraId") Integer infraId, @Param("ip") Long ip, Pageable pageable);
	
	@Query("SELECT s FROM Subnet s WHERE s.infraId = :infraId AND s.ip >= :ip AND (s.ip + s.size) < (:ip + :size)")
	public Page<Subnet> findAllByInfraIdAndIpAndSize(@Param("infraId") Integer infraId, @Param("ip") Long ip, @Param("size") Long size, Pageable pageable);

	public Subnet findByInfraIdAndId(Integer infraId, Long id);

	public void deleteByInfraIdAndId(Integer infraId, Long id);
}
