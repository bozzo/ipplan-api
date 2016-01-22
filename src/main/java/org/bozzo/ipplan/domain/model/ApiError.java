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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author boris
 *
 */
public enum ApiError {

	INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal server error."),

	BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad request, check request parameters."),

	DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, 401, "Integrity violation."),
	
	NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Object not found."),
	
	INFRA_NOT_FOUND(HttpStatus.NOT_FOUND, 1404, "No infrastructure found."),

	SUBNEET_NOT_FOUND(HttpStatus.NOT_FOUND, 2404, "No subnet found."),
	SUBNET_FULL(HttpStatus.NOT_FOUND, 2405, "No free IP address found, subnet is full."),
	
	ZONE_NOT_FOUND(HttpStatus.NOT_FOUND, 3404, "No zone found."),
	
	RANGE_NOT_FOUND(HttpStatus.NOT_FOUND, 4404, "No range found."),

	IP_NOT_IN_SUBNET(HttpStatus.BAD_REQUEST, 5400, "IP address does not belong to the given subnet or subnet does exists."),
	IP_CONFLICT(HttpStatus.NOT_FOUND, 5401, "IP address already exists, use update if you want to modify it or change IP address."),
	IP_NOT_FOUND(HttpStatus.NOT_FOUND, 5404, "IP address not found."),
	
	;
	
	private final HttpStatus status;
	private final Integer code;
	private final String message;
	
	/**
	 * @param code
	 * @param message
	 */
	ApiError(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the Http status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the message
	 */
	@JsonIgnore
	public static ResponseEntity<ApiError> getResponseEntity(ApiError error) {
		return new ResponseEntity<>(error, error.status);
	}
}
