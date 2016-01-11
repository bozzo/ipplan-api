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
package org.bozzo.ipplan.web.assembler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Address;
import org.bozzo.ipplan.domain.model.ui.AddressResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author boris
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
public class AddressResourceAssemblerTest {

	@Autowired
	private AddressResourceAssembler assembler; 

	@Test
	public void toResources_with_empty_array_should_return_an_empty_array() {
		List<AddressResource> resources = this.assembler.toResources(new ArrayList<>());
		Assert.assertNotNull(resources);
		Assert.assertEquals(0, resources.size());
	}

	@Test
	public void toResources_with_singleton_array_should_return_a_singleton_array() {
		Address address = new Address();
		address.setDescription("Test description 3");
		address.setInfraId(1);
		address.setSubnetId(1L);
		address.setLocation("somewhere");
		address.setIp(0xD1A80001L);
		address.setMac("000000000100");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 02");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		List<AddressResource> resources = this.assembler.toResources(Collections.singletonList(address));
		Assert.assertNotNull(resources);
		Assert.assertEquals(1, resources.size());
		Assert.assertTrue(ResourceSupport.class.isAssignableFrom(resources.get(0).getClass()));
	}

	@Test
	public void toResources_with_array_should_return_a_array() {
		List<Address> list = new ArrayList<>();
		Address address = new Address();
		address.setDescription("Test description 3");
		address.setInfraId(1);
		address.setSubnetId(1L);
		address.setLocation("somewhere");
		address.setIp(0xD1A80001L);
		address.setMac("000000000100");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 02");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		list.add(address);
		list.add(address);
		list.add(address);
		List<AddressResource> resources = this.assembler.toResources(list);
		Assert.assertNotNull(resources);
		Assert.assertEquals(list.size(), resources.size());
		Assert.assertTrue(ResourceSupport.class.isAssignableFrom(resources.get(0).getClass()));
	}
}
