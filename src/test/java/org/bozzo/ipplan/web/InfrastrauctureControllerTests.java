package org.bozzo.ipplan.web;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InfrastrauctureControllerTests {
	
	private static int id, id2;
	
	@Autowired
	private InfrastructureController controller;

	@Test
	public void a_get_all_should_return_empty_array() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(0, 255));
		Assert.assertNotNull(infras);
		Assert.assertTrue(infras.isEmpty());
	}

	@Test
	public void b_add_infra_should_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description");
		infra.setGroup("group");
		Infrastructure infraReturned = this.controller.addInfrastructure(infra);
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		Assert.assertEquals(infra, infraReturned);
		id = infraReturned.getId();
	}

	@Test
	public void c_get_all_should_return_an_array_with_one_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(0, 255));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void d_update_infra_shouldnt_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description 2");
		infra.setGroup("group");
		infra.setId(id);
		Infrastructure infraReturned = this.controller.updateInfrastructure(id, infra);
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		Assert.assertEquals(infra, infraReturned);
	}

	@Test
	public void e_get_all_should_return_an_array_with_one_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(0, 255));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void f_add_infra_shouldnt_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description 3");
		infra.setGroup("group");
		Infrastructure infraReturned = this.controller.addInfrastructure(infra);
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		Assert.assertEquals(infra, infraReturned);
		id2 = infraReturned.getId();
	}

	@Test
	public void g_get_all_should_return_an_array_with_two_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(0, 255));
		Assert.assertNotNull(infras);
		Assert.assertEquals(2, infras.size());
	}

	@Test
	public void h_get_all_should_return_an_array_with_one_elem_with_page() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(0, 1));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void i_get_all_should_return_an_array_with_two_elem_with_null_page() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null));
		Assert.assertNotNull(infras);
		Assert.assertEquals(2, infras.size());
	}

	@Test
	public void j_get_infra_should_return_second_infra() {
		Infrastructure infra = this.controller.getInfrastructure(id2);
		Assert.assertNotNull(infra);
		Assert.assertEquals("Test description 3", infra.getDescription());
	}

	@Test
	public void k_delete_infra_should_work() {
		this.controller.deleteInfrastructure(id2);
	}

	@Test
	public void l_get_infra_shouldnt_return_infra() {
		Infrastructure infra = this.controller.getInfrastructure(id2);
		Assert.assertNull(infra);
	}

	@Test
	public void m_delete_infra_should_work() {
		this.controller.deleteInfrastructure(id);
	}

	@Test
	public void n_get_all_should_return_an_array_with_two_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.controller.getInfrastructures(0, 255));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
