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
public class ZoneTest {
	
	@Test
	public void hashcode_equals_should_work() {
		Zone zone = new Zone();
		zone.setDescription("Test description");
		zone.setInfraId(1);
		zone.setIp(0xC0A80001L);
		
		Zone zone2 = new Zone();
		zone2.setDescription("Test description2");
		zone2.setInfraId(2);
		zone2.setIp(0xC0A80002L);

		Assert.assertEquals(zone, zone);
		Assert.assertNotEquals(zone, null);
		Assert.assertNotEquals(null, zone2);
		Assert.assertNotEquals(zone, new String());
		
		Assert.assertNotEquals(zone, zone2);
		Assert.assertNotEquals(zone.hashCode(), zone2.hashCode());
		
		zone2.setInfraId(1);
		
		Assert.assertNotEquals(zone, zone2);
		Assert.assertNotEquals(zone.hashCode(), zone2.hashCode());
		
		zone2.setIp(0xC0A80001L);
		
		Assert.assertEquals(zone,zone2);
		Assert.assertEquals(zone.hashCode(), zone2.hashCode());

		zone.setInfraId(null);
		
		Assert.assertNotEquals(zone,zone2);
		Assert.assertNotEquals(zone.hashCode(), zone2.hashCode());
		
		zone2.setInfraId(null);
		zone.setIp(null);
		
		Assert.assertNotEquals(zone, zone2);
		Assert.assertNotEquals(zone.hashCode(), zone2.hashCode());

		zone2.setIp(null);
		
		Assert.assertEquals(zone,zone2);
		Assert.assertEquals(zone.hashCode(), zone2.hashCode());
	}
}
