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

import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

/**
 * @author boris
 *
 */
public class Netmask {

	private Netmask() {}

	/**
	 * Return the netmask from the number of hosts
	 * @param number the number of hosts
	 * @return the netmask
	 */
	public static Integer fromNumberHosts(Long number) {
		Preconditions.checkArgument(number != null, "Hosts number shouldn't be null");
		Preconditions.checkArgument(number > 0, "Hosts number should be a positive integer");
		if (number >= (1L << 32)) {
			return 0;
		} else if (number >= (1L << 31)) {
			return 1;
		}
		return 1 + Integer.numberOfLeadingZeros(number.intValue());
	}
	
	/**
	 * Return the netmask as a string in format x.x.x.x
	 * @param netmask the integer value of the netmask
	 * @return a string representing the netmask
	 */
	public static String toString(Integer netmask) {
		Preconditions.checkArgument(netmask != null, "Netmask shouldn't be null");
		Preconditions.checkArgument(netmask >= 0 && netmask <= 32, "Netmask should be an integer between 0 and 32");
		
		long mask = 0;
        for (int shift = netmask; shift > 0; shift--) {
            mask += 1 << 32 - shift;
        }
        return IpAddress.toString(mask);
	}

	/**
	 * Check the validity of the network address
	 * @param ip the IP address as {@link Long} value
	 * @param size the network size
	 * @return true if it's a valid network address, false otherwise
	 */
	public static boolean isValidNetmask(Long size) {
		Preconditions.checkArgument(size != null, "Network size shouldn't be null");
		
		return size > 0 && size <= (1L << 32) && Long.bitCount(size) == 1;
	}

	/**
	 * Check the validity of the network address
	 * @param ip the IP address as {@link Long} value
	 * @param size the network size
	 * @return true if it's a valid network address, false otherwise
	 */
	public static Map<Long, String> getOptionNetmask() {
		Map<Long, String> options = new TreeMap<>();
		for (Integer mask = 0; mask <= 32; mask++) {
			options.put(1L << 32 - mask, Netmask.toString(mask) + " / " + mask);
		}
		return options;
	}
}
