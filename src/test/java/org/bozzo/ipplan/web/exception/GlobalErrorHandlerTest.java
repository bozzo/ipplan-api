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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * @author boris
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GlobalErrorHandlerTest {

	@Autowired
	private GlobalErrorHandler errorHandler;
	
    @Test
    public void handleIllegalArgumentException_should_return_ApiError() {
    	ResponseEntity<ApiError> resp = this.errorHandler.handleIllegalArgumentException(new IllegalArgumentException());
    	Assert.assertNotNull(resp);
    	Assert.assertNotNull(resp.getBody());
    	Assert.assertEquals(ApiError.BAD_REQUEST, resp.getBody());
    }
    
    @Test
    public void handleJsonParseException_should_return_ApiError() {
    	ResponseEntity<ApiError> resp = this.errorHandler.handleJsonParseException(new JsonParseException("error", null));
    	Assert.assertNotNull(resp);
    	Assert.assertNotNull(resp.getBody());
    	Assert.assertEquals(ApiError.BAD_JSON, resp.getBody());
    }

    @Test
    public void handleDataIntegrityViolationException_should_return_ApiError() {
    	ResponseEntity<ApiError> resp = this.errorHandler.handleDataIntegrityViolationException(new DataIntegrityViolationException("error"));
    	Assert.assertNotNull(resp);
    	Assert.assertNotNull(resp.getBody());
    	Assert.assertEquals(ApiError.DATA_INTEGRITY_VIOLATION, resp.getBody());
    }

    @Test
    public void handleApiException_should_return_ApiError() {
    	HttpEntity<ApiError> resp = this.errorHandler.handleApiException(new ApiException(ApiError.BAD_NETWORK));
    	Assert.assertNotNull(resp);
    	Assert.assertNotNull(resp.getBody());
    	Assert.assertEquals(ApiError.BAD_NETWORK, resp.getBody());
    }

    @Test
    public void handleException_should_return_ApiError() {
    	ResponseEntity<ApiError> resp = this.errorHandler.handleException(new Exception("error"));
    	Assert.assertNotNull(resp);
    	Assert.assertNotNull(resp.getBody());
    	Assert.assertEquals(ApiError.INTERNAL_ERROR, resp.getBody());
    }
}
