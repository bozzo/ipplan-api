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
package org.bozzo.ipplan.domain.exception;

import org.bozzo.ipplan.domain.model.ApiError;

/**
 * @author boris
 *
 */
public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2536774424606578383L;

	private final ApiError error;

	/**
	 * @param message
	 */
	public ApiException(ApiError error) {
		super(error.getMessage());
		this.error = error;
	}

	/**
	 * @return the error
	 */
	public ApiError getError() {
		return error;
	}

}
