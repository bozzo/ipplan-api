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

import org.junit.Test;

/**
 * @author boris
 *
 */
public class IPAddressTest {

	@Test(expected=IllegalArgumentException.class)
	public void toString_with_null_arg_should_throw_IAE() {
		IpAddress.toString(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void isNetworkAddress_with_null_arg_should_throw_IAE() {
		IpAddress.isNetworkAddress(null, 32L);
	}

	@Test(expected=IllegalArgumentException.class)
	public void isNetworkAddress_with_null_arg_should_throw_IAE2() {
		IpAddress.isNetworkAddress(0xD0A800000L, null);
	}

}
