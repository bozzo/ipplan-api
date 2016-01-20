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

import java.util.ArrayList;
import java.util.List;

import org.bozzo.ipplan.domain.dao.AddressRepository;
import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.model.Address;
import org.bozzo.ipplan.domain.model.Subnet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author boris
 *
 */
@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private SubnetRepository subnetRepository;

	public Page<Address> findFreeBySubnetId(Long subnetId, Pageable pageable) {
		Subnet subnet = this.subnetRepository.findOne(subnetId);
		List<Long> addresses = this.addressRepository.findAllIpBySubnetId(subnetId);
		ArrayList<Address> list = new ArrayList<>();

		int start = 0;
		int startPage = 0;
		int pageSize = 1024;
		long totalElem = 0;

		if (pageable != null) {
			startPage = pageable.getPageNumber() * pageable.getPageSize();
			pageSize = pageable.getPageSize();
		} else {
			pageable = new PageRequest(0, pageSize);
		}

		for (long ip = subnet.getIp() + 1; ip < subnet.getIp() + subnet.getSize() - 1; ip++) {
			if (!addresses.contains(ip)) {
				totalElem++;
				if (start >= startPage && (startPage + list.size()) < (startPage + pageSize)) {
					Address address = new Address();
					address.setSubnetId(subnetId);
					address.setInfraId(subnet.getInfraId());
					address.setIp(ip);
					address.setFree(true);
					list.add(address);
				} else {
					start++;
				}
			}
		}

		return new PageImpl<>(list, pageable, totalElem);
	}

	public Address findFreeAddressBySubnetId(Long subnetId) {
		Subnet subnet = this.subnetRepository.findOne(subnetId);
		List<Long> addresses = this.addressRepository.findAllIpBySubnetId(subnetId);

		for (long ip = subnet.getIp() + 1; ip < subnet.getIp() + subnet.getSize() - 1; ip++) {
			if (!addresses.contains(ip)) {
				Address address = new Address();
				address.setSubnetId(subnetId);
				address.setInfraId(subnet.getInfraId());
				address.setIp(ip);
				address.setFree(true);
				return address;
			}
		}

		return null;
	}
}
