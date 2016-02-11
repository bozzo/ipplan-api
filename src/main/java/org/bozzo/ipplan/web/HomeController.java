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
package org.bozzo.ipplan.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author boris
 *
 */
@RestController
public class HomeController {

	@RequestMapping(value="/api", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	@ApiIgnore
	public ModelAndView getApiView() {
		return new ModelAndView("redirect:/api/infras");
	}

	@RequestMapping(value="/", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	@ApiIgnore
	public ModelAndView getHomeView() {
		return new ModelAndView("redirect:/api/infras");
	}

}
