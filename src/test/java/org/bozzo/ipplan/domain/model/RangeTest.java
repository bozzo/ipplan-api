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
public class RangeTest {
	
	@Test
	public void hashcode_equals_should_work() {
		Range range = new Range();
		range.setDescription("Test description");
		range.setInfraId(1);
		range.setZoneId(1L);
		range.setSize(65535L);
		range.setIp(0xC0A80001L);
		
		Range range2 = new Range();
		range2.setDescription("Test description");
		range2.setInfraId(2);
		range2.setZoneId(2L);
		range2.setSize(65536L);
		range2.setIp(0xC0A80002L);

		Assert.assertEquals(range, range);
		Assert.assertNotEquals(range, null);
		Assert.assertNotEquals(null, range2);
		Assert.assertNotEquals(range, new String());
		
		Assert.assertNotEquals(range, range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setInfraId(1);
		
		Assert.assertNotEquals(range, range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setIp(0xC0A80001L);
		
		Assert.assertNotEquals(range, range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setSize(65535L);
		
		Assert.assertNotEquals(range, range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setZoneId(1L);
		
		Assert.assertEquals(range,range2);
		Assert.assertEquals(range.hashCode(), range2.hashCode());

		range.setInfraId(null);
		
		Assert.assertNotEquals(range,range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setInfraId(null);
		range.setIp(null);
		
		Assert.assertNotEquals(range, range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());

		range2.setIp(null);
		range.setSize(null);
		
		Assert.assertNotEquals(range,range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setSize(null);
		range.setZoneId(null);
		
		Assert.assertNotEquals(range,range2);
		Assert.assertNotEquals(range.hashCode(), range2.hashCode());
		
		range2.setZoneId(null);
		
		Assert.assertEquals(range,range2);
		Assert.assertEquals(range.hashCode(), range2.hashCode());
	}
}
