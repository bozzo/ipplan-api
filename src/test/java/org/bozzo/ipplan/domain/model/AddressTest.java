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
package org.bozzo.ipplan.domain.model;

import java.util.Date;

import org.bozzo.ipplan.IpplanApiApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author boris
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
public class AddressTest {
	
	@Test
	public void hashcode_equals_should_work() {
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(1L);
		address.setInfraId(1);
		address.setLocation("somewhere");
		address.setIp(0xD0A80001L);
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		
		Address address2 = new Address();
		address2.setDescription("Test description 2");
		address2.setSubnetId(2L);
		address2.setInfraId(2);
		address2.setLocation("somewhere2");
		address2.setIp(0xD0A80002L);
		address2.setMac("000000000001");
		address2.setLastModifed(new Date());
		address2.setLastPol(new Date());
		address2.setName("My Server 02");
		address2.setPhone("0000000002");
		address2.setUserId("user2");
		address2.setUserInfo("My user2");

		Assert.assertEquals(address, address);
		Assert.assertNotEquals(address, null);
		Assert.assertNotEquals(null, address2);
		Assert.assertNotEquals(address, new String());
		
		Assert.assertNotEquals(address, address2);
		Assert.assertNotEquals(address.hashCode(), address2.hashCode());
		
		address2.setIp(0xD0A80001L);
		
		Assert.assertNotEquals(address, address2);
		Assert.assertNotEquals(address.hashCode(), address2.hashCode());
		
		address2.setSubnetId(1L);
		
		Assert.assertEquals(address,address2);
		Assert.assertEquals(address.hashCode(), address2.hashCode());
		
		address.setIp(null);
		
		Assert.assertNotEquals(address, address2);
		Assert.assertNotEquals(address.hashCode(), address2.hashCode());

		address2.setIp(null);
		address.setSubnetId(null);
		
		Assert.assertNotEquals(address,address2);
		Assert.assertNotEquals(address.hashCode(), address2.hashCode());
		
		address2.setSubnetId(null);
		
		Assert.assertEquals(address,address2);
		Assert.assertEquals(address.hashCode(), address2.hashCode());
	}
}
