/*
 * Copyright (C) 2016
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
package org.bozzo.ipplan.domain.service;

import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.dao.RangeRepository;
import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author boris
 *
 */
@Service
public class RangeService {

	@Autowired
	private ZoneRepository zoneRepository;

	@Autowired
	private RangeRepository rangeRepository;

	public Range save(Range range) {
		if (! this.zoneRepository.exists(range.getZoneId())) {
			throw new ApiException(ApiError.ZONE_NOT_FOUND);
		} else if (range.getId() != null && this.rangeRepository.existsRangeConflict(range.getId(), range.getIp(), range.getSize())) {
			throw new ApiException(ApiError.RANGE_CONFLICT);
		} else if (range.getId() == null && this.rangeRepository.existsRangeConflict(range.getIp(), range.getSize())) {
			throw new ApiException(ApiError.RANGE_CONFLICT);
		}
		return this.rangeRepository.save(range);
	}
}
