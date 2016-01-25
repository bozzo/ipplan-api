package org.bozzo.ipplan.web;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InfrastrauctureControllerTests {
	
	private static int id, id2;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController controller;

	@Test
	public void a_get_all_should_return_empty_array() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertTrue(infras.isEmpty());
	}

	@Test
	public void b_add_infra_should_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description");
		infra.setGroup("group");
		HttpEntity<InfrastructureResource> resp = this.controller.addInfrastructure(infra);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		InfrastructureResource infraReturned = resp.getBody();
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		Assert.assertEquals(3, infraReturned.getLinks().size());
		id = infraReturned.getInfraId();
	}

	@Test
	public void c_get_all_should_return_an_array_with_one_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void d_update_infra_shouldnt_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description 2");
		infra.setGroup("group");
		infra.setId(id);
		HttpEntity<InfrastructureResource> response = this.controller.updateInfrastructure(id, infra);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		InfrastructureResource infraReturned = response.getBody();
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		Assert.assertEquals(3, infraReturned.getLinks().size());
	}

	@Test
	public void e_get_all_should_return_an_array_with_one_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void f_add_infra_shouldnt_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description 3");
		infra.setGroup("group2");
		HttpEntity<InfrastructureResource> response = this.controller.addInfrastructure(infra);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		InfrastructureResource infraReturned = response.getBody();
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		Assert.assertEquals(3, infraReturned.getLinks().size());
		id2 = infraReturned.getInfraId();
	}

	@Test
	public void g_get_all_should_return_an_array_with_two_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(2, infras.size());
	}

	@Test
	public void h_get_all_should_return_an_array_with_one_elem_with_page() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, new PageRequest(0, 1), new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void i_get_all_should_return_an_array_with_two_elem_with_null_page() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(2, infras.size());
	}

	@Test
	public void j_get_infra_should_return_second_infra() {
		HttpEntity<InfrastructureResource> response = this.controller.getInfrastructure(id2);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		InfrastructureResource infra = response.getBody();
		Assert.assertNotNull(infra);
		Assert.assertEquals("Test description 3", infra.getDescription());
		Assert.assertEquals(3, infra.getLinks().size());
	}

	@Test
	public void k_get_infras_with_search_should_return_first_infra() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures("group", new PageRequest(0, 1), new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
		InfrastructureResource infra = infras.get(0);
		Assert.assertNotNull(infra);
		Assert.assertEquals("Test description 2", infra.getDescription());
		Assert.assertEquals(3, infra.getLinks().size());
	}

	@Test
	public void l_delete_infra_should_work() {
		this.controller.deleteInfrastructure(id2);
	}

	@Test
	public void m_get_infra_shouldnt_return_infra() {
		HttpEntity<InfrastructureResource> response;
		try {
			response = this.controller.getInfrastructure(id2);
			Assert.assertNull(response);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.INFRA_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void view_get_view_all_should_return_an_array_with_two_elem() {
		ModelAndView view = this.controller.getInfrastructuresView(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("infras", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
		ModelAndView view = this.controller.getInfrastructureView(id);
		Assert.assertNotNull(view);
		Assert.assertEquals("infra", view.getViewName());
	}

	@Test
	public void z1_delete_infra_should_work() {
		this.controller.deleteInfrastructure(id);
	}

	@Test
	public void z2_get_all_should_return_an_array_with_two_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.controller.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
