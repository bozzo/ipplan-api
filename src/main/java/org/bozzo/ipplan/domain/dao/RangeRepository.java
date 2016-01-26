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

import org.bozzo.ipplan.domain.model.Range;
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
public interface RangeRepository extends PagingAndSortingRepository<Range, Long> {

	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN 'true' ELSE 'false' END "
			+ "FROM Range r "
			+ "WHERE  ( "
				+ "(r.ip >= :ip AND r.ip < (:ip + :size)) "
				+ "OR ((r.ip + r.size) > :ip AND (r.ip + r.size) <= (:ip + :size)) "
				+ "OR (:ip >= r.ip AND :ip < (r.ip + r.size)) "
				+ "OR ((:ip + :size) > r.ip AND (:ip + :size) <= (r.ip + r.size)) "
				+ ") AND NOT r.id = :rangeId")
	public Boolean existsRangeConflict(@Param("rangeId") Long rangeId, @Param("ip") Long ip, @Param("size") Long size);

	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN 'true' ELSE 'false' END "
			+ "FROM Range r "
			+ "WHERE  ( "
				+ "(r.ip >= :ip AND r.ip < (:ip + :size)) "
				+ "OR ((r.ip + r.size) > :ip AND (r.ip + r.size) <= (:ip + :size)) "
				+ "OR (:ip >= r.ip AND :ip < (r.ip + r.size)) "
				+ "OR ((:ip + :size) > r.ip AND (:ip + :size) <= (r.ip + r.size)) "
				+ ")")
	public Boolean existsRangeConflict(@Param("ip") Long ip, @Param("size") Long size);

	public Page<Range> findByInfraIdAndZoneId(Integer infraId, Long zoneId, Pageable pageable);

	public Range findByInfraIdAndZoneIdAndId(Integer infraId, Long zoneId, Long id);

	public Range findByInfraIdAndZoneIdAndIp(Integer infraId, Long zoneId, Long ip);

	public void deleteByInfraIdAndZoneIdAndId(Integer infraId, Long zoneId, Long id);

}
