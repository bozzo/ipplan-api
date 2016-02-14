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
public class AddressPlanTest {
	
	@Test
	public void hashcode_equals_should_work() {
		AddressPlan plan = new AddressPlan();
		plan.setDescription("Test description 2");
		plan.setInfraId(1);
		plan.setLastModifed(new Date());
		plan.setName("test");
		
		AddressPlan plan2 = new AddressPlan();
		plan2.setDescription("Test description 2");
		plan2.setInfraId(2);
		plan2.setLastModifed(new Date());
		plan2.setName("test2");

		Assert.assertEquals(plan, plan);
		Assert.assertNotEquals(plan, null);
		Assert.assertNotEquals(null, plan2);
		Assert.assertNotEquals(plan, new String());
		
		Assert.assertNotEquals(plan, plan2);
		Assert.assertNotEquals(plan.hashCode(), plan2.hashCode());
		
		plan2.setInfraId(1);
		
		Assert.assertNotEquals(plan, plan2);
		Assert.assertNotEquals(plan.hashCode(), plan2.hashCode());
		
		plan2.setName("test");
		
		Assert.assertEquals(plan,plan2);
		Assert.assertEquals(plan.hashCode(), plan2.hashCode());

		plan.setInfraId(null);
		
		Assert.assertNotEquals(plan,plan2);
		Assert.assertNotEquals(plan.hashCode(), plan2.hashCode());
		
		plan2.setInfraId(null);
		plan.setName(null);
		
		Assert.assertNotEquals(plan, plan2);
		Assert.assertNotEquals(plan.hashCode(), plan2.hashCode());

		plan2.setName(null);
		
		Assert.assertEquals(plan,plan2);
		Assert.assertEquals(plan.hashCode(), plan2.hashCode());
	}
}
