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
package org.bozzo.ipplan.tools;

import com.google.common.base.Preconditions;

/**
 * @author boris
 *
 */
public class IpAddress {

	private IpAddress() {}
	
	/**
	 * Return the long value of an IP address
	 * @param ip the IP address in {@link String} format x.x.x.x
	 * @return the long value
	 */
	public static Long toLong(String ip) {

		long address = 0;

		String[] ipDigits = ip.split("\\.");

		for (int index = 3; index >= 0; index--) {
			address |= Long.parseUnsignedLong(ipDigits[3 - index]) << (index * 8);
		}
		return new Long(address);
	}

	/**
	 * Return the format format of an IP address
	 * @param ip the IP address as {@link Long} value
	 * @return the IP address in format x.x.x.x
	 */
	public static String toString(Long ip) {
		Preconditions.checkArgument(ip != null, "IP address shouldn't be null");
		
		return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
	}

	/**
	 * Check the validity of the network address
	 * @param ip the IP address as {@link Long} value
	 * @param size the network size
	 * @return true if it's a valid network address, false otherwise
	 */
	public static boolean isNetworkAddress(Long ip, Long size) {
		Preconditions.checkArgument(ip != null, "IP address shouldn't be null");
		Preconditions.checkArgument(size != null, "Network size shouldn't be null");
		
		return (ip % size) == 0;
	}
}
