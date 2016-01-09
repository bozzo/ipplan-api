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
package org.bozzo.ipplan.tools;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author boris
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NetmaskTests {

	@Test
	public void a_toString_should_return_the_netmask() {
		Assert.assertEquals("0.0.0.0", Netmask.toString(0));
		Assert.assertEquals("128.0.0.0", Netmask.toString(1));
		Assert.assertEquals("192.0.0.0", Netmask.toString(2));
		Assert.assertEquals("224.0.0.0", Netmask.toString(3));
		Assert.assertEquals("240.0.0.0", Netmask.toString(4));
		Assert.assertEquals("248.0.0.0", Netmask.toString(5));
		Assert.assertEquals("252.0.0.0", Netmask.toString(6));
		Assert.assertEquals("254.0.0.0", Netmask.toString(7));
		Assert.assertEquals("255.0.0.0", Netmask.toString(8));
		Assert.assertEquals("255.128.0.0", Netmask.toString(9));
		Assert.assertEquals("255.192.0.0", Netmask.toString(10));
		Assert.assertEquals("255.224.0.0", Netmask.toString(11));
		Assert.assertEquals("255.240.0.0", Netmask.toString(12));
		Assert.assertEquals("255.248.0.0", Netmask.toString(13));
		Assert.assertEquals("255.252.0.0", Netmask.toString(14));
		Assert.assertEquals("255.254.0.0", Netmask.toString(15));
		Assert.assertEquals("255.255.0.0", Netmask.toString(16));
		Assert.assertEquals("255.255.128.0", Netmask.toString(17));
		Assert.assertEquals("255.255.192.0", Netmask.toString(18));
		Assert.assertEquals("255.255.224.0", Netmask.toString(19));
		Assert.assertEquals("255.255.240.0", Netmask.toString(20));
		Assert.assertEquals("255.255.248.0", Netmask.toString(21));
		Assert.assertEquals("255.255.252.0", Netmask.toString(22));
		Assert.assertEquals("255.255.254.0", Netmask.toString(23));
		Assert.assertEquals("255.255.255.0", Netmask.toString(24));
		Assert.assertEquals("255.255.255.128", Netmask.toString(25));
		Assert.assertEquals("255.255.255.192", Netmask.toString(26));
		Assert.assertEquals("255.255.255.224", Netmask.toString(27));
		Assert.assertEquals("255.255.255.240", Netmask.toString(28));
		Assert.assertEquals("255.255.255.248", Netmask.toString(29));
		Assert.assertEquals("255.255.255.252", Netmask.toString(30));
		Assert.assertEquals("255.255.255.254", Netmask.toString(31));
		Assert.assertEquals("255.255.255.255", Netmask.toString(32));
	}

}
