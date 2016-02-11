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
package org.bozzo.ipplan.web.json;

import java.io.IOException;

import org.bozzo.ipplan.domain.ApiError;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author boris
 *
 */
public class ApiErrorSerializerTest {
	
	@Test
	public void serialize_null_value_should_work() throws JsonProcessingException, IOException {
		JsonGenerator generator = Mockito.mock(JsonGenerator.class);
		SerializerProvider provider = Mockito.mock(SerializerProvider.class);
		ApiErrorSerializer serializer = new ApiErrorSerializer();
		serializer.serialize(null, generator, provider);
		Mockito.verify(generator).writeNull();
	}
	
	@Test
	public void serialize_io_exception_value_should_work() throws JsonProcessingException, IOException {
		JsonGenerator generator = Mockito.mock(JsonGenerator.class);
		SerializerProvider provider = Mockito.mock(SerializerProvider.class);
		
		ApiErrorSerializer serializer = new ApiErrorSerializer();
		serializer.serialize(ApiError.IP_CONFLICT, generator, provider);
		Mockito.verify(generator, Mockito.times(2)).writeStringField(Mockito.anyString(), Mockito.anyString());
	}
}
