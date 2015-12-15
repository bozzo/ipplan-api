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
package org.bozzo.ipplan.config;

/**
 * @author boris
 *
 */
public final class IpplanConfig {

	public static final String API_PATH = "/api";
	public static final String INFRAS_PATH = "/infras";
	public static final String ZONES_PATH = "/zones";
	public static final String RANGES_PATH = "/ranges";
	/**
	 * 
	 */
	private IpplanConfig() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getInfraLink(Integer infraId) {
		return API_PATH + INFRAS_PATH + "/" + infraId;
	}
	
	public static String getZonesLink(Integer infraId) {
		return getInfraLink(infraId) + ZONES_PATH;
	}
	
	public static String getZoneLink(Integer infraId, Long zoneId) {
		return getZonesLink(infraId) + "/" + zoneId;
	}
	
	public static String getRangesLink(Integer infraId, Long zoneId) {
		return getZoneLink(infraId, zoneId) + RANGES_PATH;
	}
	
	public static String getRangeLink(Integer infraId, Long zoneId, Long rangeIp) {
		return getRangesLink(infraId, zoneId) + "/" + rangeIp;
	}

}
