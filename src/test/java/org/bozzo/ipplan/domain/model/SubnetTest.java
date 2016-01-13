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
public class SubnetTest {
	
	@Test
	public void hashcode_equals_should_work() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(1);
		subnet.setGroup("group");
		subnet.setIp(0xD0A80001L);
		subnet.setSize(65535L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		
		Subnet subnet2 = new Subnet();
		subnet2.setDescription("Test description2");
		subnet2.setInfraId(2);
		subnet2.setGroup("group2");
		subnet2.setIp(0xD0A80002L);
		subnet2.setSize(32L);
		subnet2.setLastModifed(new Date());
		subnet2.setOptionId(2L);
		subnet2.setSwipMod(new Date());
		subnet2.setUserId("user2");

		Assert.assertEquals(subnet, subnet);
		Assert.assertNotEquals(subnet, null);
		Assert.assertNotEquals(null, subnet2);
		Assert.assertNotEquals(subnet, new String());
		
		Assert.assertNotEquals(subnet, subnet2);
		Assert.assertNotEquals(subnet.hashCode(), subnet2.hashCode());
		
		subnet2.setInfraId(1);
		
		Assert.assertNotEquals(subnet,subnet2);
		Assert.assertNotEquals(subnet.hashCode(), subnet2.hashCode());
		
		subnet2.setIp(0xD0A80001L);
		
		Assert.assertNotEquals(subnet, subnet2);
		Assert.assertNotEquals(subnet.hashCode(), subnet2.hashCode());
		
		subnet2.setSize(65535L);
		
		Assert.assertEquals(subnet,subnet2);
		Assert.assertEquals(subnet.hashCode(), subnet2.hashCode());
		
		subnet.setInfraId(null);
		
		Assert.assertNotEquals(subnet,subnet2);
		Assert.assertNotEquals(subnet.hashCode(), subnet2.hashCode());
		
		subnet2.setInfraId(null);
		subnet.setIp(null);
		
		Assert.assertNotEquals(subnet, subnet2);
		Assert.assertNotEquals(subnet.hashCode(), subnet2.hashCode());

		subnet2.setIp(null);
		subnet.setSize(null);
		
		Assert.assertNotEquals(subnet,subnet2);
		Assert.assertNotEquals(subnet.hashCode(), subnet2.hashCode());
		
		subnet2.setSize(null);
		
		Assert.assertEquals(subnet,subnet2);
		Assert.assertEquals(subnet.hashCode(), subnet2.hashCode());
	}
}
