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
package org.bozzo.ipplan.domain.functions;

import java.util.function.Function;

import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.ui.RangeResource;

/**
 * @author boris
 *
 */
public class ToRangeResourceFunction implements Function<Range, RangeResource> {

	@Override
	public RangeResource apply(Range t) {
		return new RangeResource(t);
	}
}
