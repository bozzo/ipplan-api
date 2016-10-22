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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author boris
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InfrastructureTest {
	
	@Test
	public void hashcode_equals_should_work() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description");
		infra.setGroup("group");
		infra.setId(1);
		infra.setCrm("crm");
		
		Infrastructure infra2 = new Infrastructure();
		infra2.setDescription("Test description2");
		infra2.setGroup("group2");
		infra2.setId(2);
		infra2.setCrm("crm2");

		Assert.assertEquals(infra, infra);
		Assert.assertNotEquals(infra, null);
		Assert.assertNotEquals(null, infra2);
		Assert.assertNotEquals(infra, new String());
		
		Assert.assertNotEquals(infra, infra2);
		Assert.assertNotEquals(infra.hashCode(), infra2.hashCode());
		
		infra2.setId(1);
		
		Assert.assertEquals(infra,infra2);
		Assert.assertEquals(infra.hashCode(), infra2.hashCode());

		infra.setId(null);
		
		Assert.assertNotEquals(infra,infra2);
		Assert.assertNotEquals(infra.hashCode(), infra2.hashCode());
		
		infra2.setId(null);
		
		Assert.assertEquals(infra,infra2);
		Assert.assertEquals(infra.hashCode(), infra2.hashCode());
	}
}
