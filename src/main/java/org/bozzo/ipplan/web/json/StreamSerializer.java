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
import java.io.UncheckedIOException;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author boris
 *
 */
public class StreamSerializer extends JsonSerializer<Stream<?>> {


	@Override
	public void serialize(Stream<?> value, JsonGenerator generator, SerializerProvider serializer)
			throws IOException, JsonProcessingException {
		if (value == null) {
			generator.writeNull();
		} else {
			generator.writeStartArray();
			value.forEach(obj -> {
		        try {
		        	generator.writeObject(obj);
		        }
		        catch(IOException e) {
		            throw new UncheckedIOException(e);
		        }});
			generator.writeEndArray();
		}
	}
	
	

}
