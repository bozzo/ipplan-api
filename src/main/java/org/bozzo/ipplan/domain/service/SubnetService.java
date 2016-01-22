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

import org.bozzo.ipplan.domain.dao.InfrastructureRepository;
import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.ApiError;
import org.bozzo.ipplan.domain.model.Subnet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author boris
 *
 */
@Service
public class SubnetService {

	@Autowired
	private SubnetRepository subnetRepository;

	@Autowired
	private InfrastructureRepository infraRepository;

	public Subnet save(Subnet subnet) {
		if (! this.infraRepository.exists(subnet.getInfraId())) {
			throw new ApiException(ApiError.INFRA_NOT_FOUND);
		} else if (subnet.getId() != null && this.subnetRepository.existsSubnetConflict(subnet.getId(), subnet.getIp(), subnet.getSize())) {
			throw new ApiException(ApiError.SUBNET_CONFLICT);
		} else if (subnet.getId() == null && this.subnetRepository.existsSubnetConflict(subnet.getIp(), subnet.getSize())) {
			throw new ApiException(ApiError.SUBNET_CONFLICT);
		}
		return this.subnetRepository.save(subnet);
	}
}
