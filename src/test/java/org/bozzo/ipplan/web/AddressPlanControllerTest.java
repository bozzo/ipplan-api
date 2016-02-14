package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.AddressPlan;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.ui.AddressPlanResource;
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
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressPlanControllerTest {

	private static long id, id2;
	private static int infraId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private AddressPlanController controller;

	@Test
	public void a_add_infra_should_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description");
		infra.setGroup("group");
		HttpEntity<InfrastructureResource> resp = this.infrastructureController.addInfrastructure(infra);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		InfrastructureResource infraReturned = resp.getBody();
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		infraId = infraReturned.getInfraId();
	}

	@Test
	public void b_get_all_should_return_an_infra_array_with_one_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void c_get_all_should_return_empty_array() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertTrue(plans.isEmpty());
	}

	@Test
	public void d_add_plan_should_create_a_new_plan() {
		AddressPlan plan = new AddressPlan();
		plan.setDescription("Test description");
		plan.setInfraId(infraId);
		plan.setLastModifed(new Date());
		plan.setName("test");
		HttpEntity<AddressPlanResource> resp = this.controller.addAddressPlan(infraId, plan);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressPlanResource planReturned = resp.getBody();
		Assert.assertNotNull(planReturned);
		Assert.assertNotNull(planReturned.getId());
		Assert.assertEquals(plan.getDescription(), planReturned.getDescription());
		Assert.assertEquals(plan.getInfraId(), planReturned.getInfraId());
		Assert.assertEquals(plan.getName(), planReturned.getName());
		Assert.assertEquals(plan.getLastModifed(), planReturned.getLastModifed());
		Assert.assertEquals(2, planReturned.getLinks().size());
		id = planReturned.getPlanId();
	}

	@Test
	public void e_get_all_should_return_an_array_with_one_elem() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(1, plans.size());
	}

	@Test
	public void f_update_plan_shouldnt_create_a_new_plan() {
		AddressPlan plan = new AddressPlan();
		plan.setDescription("Test description 2");
		plan.setInfraId(infraId);
		plan.setLastModifed(new Date());
		plan.setName("test");
		plan.setId(id);
		HttpEntity<AddressPlanResource> resp = this.controller.updateAddressPlan(infraId, id, plan);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressPlanResource planReturned = resp.getBody();
		Assert.assertNotNull(planReturned);
		Assert.assertNotNull(planReturned.getId());
		Assert.assertEquals(plan.getDescription(), planReturned.getDescription());
		Assert.assertEquals(plan.getInfraId(), planReturned.getInfraId());
		Assert.assertEquals(plan.getName(), planReturned.getName());
		Assert.assertEquals(plan.getLastModifed(), planReturned.getLastModifed());
		Assert.assertEquals(2, planReturned.getLinks().size());
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(1, plans.size());
	}

	@Test
	public void h_add_plan_shouldnt_create_a_new_plan() {
		AddressPlan plan = new AddressPlan();
		plan.setDescription("Test description 3");
		plan.setInfraId(infraId);
		plan.setLastModifed(new Date());
		plan.setName("test2");
		HttpEntity<AddressPlanResource> resp = this.controller.addAddressPlan(infraId, plan);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressPlanResource planReturned = resp.getBody();
		Assert.assertNotNull(planReturned);
		Assert.assertNotNull(planReturned.getId());
		Assert.assertEquals(plan.getDescription(), planReturned.getDescription());
		Assert.assertEquals(plan.getInfraId(), planReturned.getInfraId());
		Assert.assertEquals(plan.getName(), planReturned.getName());
		Assert.assertEquals(plan.getLastModifed(), planReturned.getLastModifed());
		Assert.assertEquals(2, planReturned.getLinks().size());
		id2 = planReturned.getPlanId();
	}

	@Test
	public void i_get_all_should_return_an_array_with_two_elem() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(2, plans.size());
	}

	@Test
	public void j_get_all_should_return_an_array_with_two_elem_with_page() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, new PageRequest(0, 1), new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(1, plans.size());
	}

	@Test
	public void k_get_all_should_return_an_array_with_two_elem_with_null_page() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(2, plans.size());
	}

	@Test
	public void l_get_plan_should_return_second_plan() {
		HttpEntity<AddressPlanResource> resp = this.controller.getAddressPlan(infraId, id2);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressPlanResource plan = resp.getBody();
		Assert.assertNotNull(plan);
		Assert.assertEquals("Test description 3", plan.getDescription());
		Assert.assertEquals("test2", plan.getName());
		Assert.assertEquals(2, plan.getLinks().size());
	}

	@Test
	public void m_delete_plan_should_work() {
		this.controller.deleteAddressPlan(infraId, id2);
	}

	@Test
	public void n_get_plan_shouldnt_return_plan() {
		HttpEntity<AddressPlanResource> resp;
		try {
			resp = this.controller.getAddressPlan(infraId, id2);
			Assert.assertNull(resp);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.PLAN_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void z1_delete_plan_should_work() {
		this.controller.deleteAddressPlan(infraId, id);
	}

	@Test
	public void z2_get_all_should_return_an_array_with_no_elem() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(0, plans.size());
	}

	@Test
	public void z3_delete_infra_should_work() {
		this.infrastructureController.deleteInfrastructure(infraId);
	}

	@Test
	public void z4_get_all_should_return_an_array_with_two_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
