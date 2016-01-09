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
import org.junit.Test;

/**
 * @author boris
 *
 */
public class NetmaskTests {

	@Test(expected=IllegalArgumentException.class)
	public void toString_with_negative_int_should_throw_IAE() {
		Netmask.toString(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void toString_with_int_grower_than_32_should_throw_IAE() {
		Netmask.toString(33);
	}

	@Test(expected=IllegalArgumentException.class)
	public void toString_with_null_arg_should_throw_IAE() {
		Netmask.toString(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void fromNumberHosts_with_negative_int_should_throw_IAE() {
		Netmask.fromNumberHosts(-1L);
	}

	@Test(expected=IllegalArgumentException.class)
	public void fromNumberHosts_with_null_arg_should_throw_IAE() {
		Netmask.fromNumberHosts(null);
	}

	@Test
	public void toString_should_return_the_netmask() {
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

	@Test
	public void fromNumberHosts_should_return_the_netmask() {
		Assert.assertEquals(32, (int) Netmask.fromNumberHosts(1L));
		Assert.assertEquals(31, (int) Netmask.fromNumberHosts(2L));
		Assert.assertEquals(30, (int) Netmask.fromNumberHosts(4L));
		Assert.assertEquals(29, (int) Netmask.fromNumberHosts(8L));
		Assert.assertEquals(28, (int) Netmask.fromNumberHosts(16L));
		Assert.assertEquals(27, (int) Netmask.fromNumberHosts(32L));
		Assert.assertEquals(26, (int) Netmask.fromNumberHosts(64L));
		Assert.assertEquals(25, (int) Netmask.fromNumberHosts(128L));
		Assert.assertEquals(24, (int) Netmask.fromNumberHosts(256L));
		Assert.assertEquals(23, (int) Netmask.fromNumberHosts(512L));
		Assert.assertEquals(22, (int) Netmask.fromNumberHosts(1024L));
		Assert.assertEquals(21, (int) Netmask.fromNumberHosts(2048L));
		Assert.assertEquals(20, (int) Netmask.fromNumberHosts(4096L));
		Assert.assertEquals(19, (int) Netmask.fromNumberHosts(8192L));
		Assert.assertEquals(18, (int) Netmask.fromNumberHosts(16384L));
		Assert.assertEquals(17, (int) Netmask.fromNumberHosts(32768L));
		Assert.assertEquals(16, (int) Netmask.fromNumberHosts(65536L));
		Assert.assertEquals(15, (int) Netmask.fromNumberHosts(131072L));
		Assert.assertEquals(14, (int) Netmask.fromNumberHosts(262144L));
		Assert.assertEquals(13, (int) Netmask.fromNumberHosts(524288L));
		Assert.assertEquals(12, (int) Netmask.fromNumberHosts(1048576L));
		Assert.assertEquals(11, (int) Netmask.fromNumberHosts(2097152L));
		Assert.assertEquals(10, (int) Netmask.fromNumberHosts(4194304L));
		Assert.assertEquals(9, (int) Netmask.fromNumberHosts(8388608L));
		Assert.assertEquals(8, (int) Netmask.fromNumberHosts(16777216L));
		Assert.assertEquals(7, (int) Netmask.fromNumberHosts(33554432L));
		Assert.assertEquals(6, (int) Netmask.fromNumberHosts(67108864L));
		Assert.assertEquals(5, (int) Netmask.fromNumberHosts(134217728L));
		Assert.assertEquals(4, (int) Netmask.fromNumberHosts(268435456L));
		Assert.assertEquals(3, (int) Netmask.fromNumberHosts(536870912L));
		Assert.assertEquals(2, (int) Netmask.fromNumberHosts(1073741824L));
		Assert.assertEquals(1, (int) Netmask.fromNumberHosts(2147483648L));
		Assert.assertEquals(0, (int) Netmask.fromNumberHosts(4294967296L));
	}

}
