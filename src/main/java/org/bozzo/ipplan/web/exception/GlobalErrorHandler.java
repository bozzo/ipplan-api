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
package org.bozzo.ipplan.web.exception;

import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author boris
 *
 */
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException() {
		return ApiError.getResponseEntity(ApiError.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException() {
		return ApiError.getResponseEntity(ApiError.DATA_INTEGRITY_VIOLATION);
    }

    @ExceptionHandler(ApiException.class)
    public HttpEntity<ApiError> handleApiException(ApiException exception) {
		return ApiError.getResponseEntity(exception.getError());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException() {
		return ApiError.getResponseEntity(ApiError.INTERNAL_ERROR);
    }
}
