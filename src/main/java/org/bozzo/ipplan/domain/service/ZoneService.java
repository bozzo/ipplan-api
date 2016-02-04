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

import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.dao.RangeRepository;
import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author boris
 *
 */
@Service
public class ZoneService {

	@Autowired
	private RangeRepository rangeRepository;

	@Autowired
	private ZoneRepository zoneRepository;
	
	public Zone findByInfraIdAndId(Integer infraId, Long id, RequestMode mode) {
		Zone subnet = this.zoneRepository.findByInfraIdAndId(infraId, id);
		if (RequestMode.FULL.equals(mode)) {
			subnet.setRanges(this.rangeRepository.findByInfraIdAndZoneId(infraId, id, null));
		}
		return subnet;
	}
}
