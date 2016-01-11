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
package org.bozzo.ipplan.web.assembler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author boris
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
public class InfrastructureResourceAssemblerTest {

	@Autowired
	private InfrastructureResourceAssembler assembler; 

	@Test
	public void toResources_with_empty_array_should_return_an_empty_array() {
		List<InfrastructureResource> resources = this.assembler.toResources(new ArrayList<>());
		Assert.assertNotNull(resources);
		Assert.assertEquals(0, resources.size());
	}

	@Test
	public void toResources_with_singleton_array_should_return_a_singleton_array() {
		Infrastructure infra = new Infrastructure();
		infra.setId(1);
		infra.setDescription("Test description");
		infra.setGroup("group");
		List<InfrastructureResource> resources = this.assembler.toResources(Collections.singletonList(infra));
		Assert.assertNotNull(resources);
		Assert.assertEquals(1, resources.size());
		Assert.assertTrue(ResourceSupport.class.isAssignableFrom(resources.get(0).getClass()));
	}

	@Test
	public void toResources_with_array_should_return_a_array() {
		List<Infrastructure> list = new ArrayList<>();
		Infrastructure infra = new Infrastructure();
		infra.setId(1);
		infra.setDescription("Test description");
		infra.setGroup("group");
		list.add(infra);
		list.add(infra);
		list.add(infra);
		List<InfrastructureResource> resources = this.assembler.toResources(list);
		Assert.assertNotNull(resources);
		Assert.assertEquals(list.size(), resources.size());
		Assert.assertTrue(ResourceSupport.class.isAssignableFrom(resources.get(0).getClass()));
	}
}
