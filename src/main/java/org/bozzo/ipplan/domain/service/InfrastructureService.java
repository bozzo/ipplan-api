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
import org.bozzo.ipplan.domain.dao.InfrastructureRepository;
import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author boris
 *
 */
@Service
public class InfrastructureService {

	@Autowired
	private ZoneRepository zoneRepository;

	@Autowired
	private SubnetRepository subnetRepository;

	@Autowired
	private InfrastructureRepository infraRepository;
	
	public Infrastructure findOne(Integer infraId, RequestMode mode) {
		Infrastructure infra = this.infraRepository.findOne(infraId);
		if (RequestMode.FULL.equals(mode)) {
			infra.setSubnets(this.subnetRepository.findAllByInfraId(infraId, null));
			infra.setZones(this.zoneRepository.findByInfraId(infraId, null));
		}
		return infra;
	}
}
