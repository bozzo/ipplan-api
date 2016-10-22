package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.dao.AddressPlanRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.AddressPlan;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.ui.AddressPlanResource;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressPlanControllerTest {

	private static long id, id2;
	private static int infraId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private AddressPlanController controller;
    
    @Autowired
    private AddressPlanRepository repository;

	@Before
	public void add_infra_should_create_a_new_infra() {
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

		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void get_all_should_return_empty_array() {
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertTrue(plans.isEmpty());
	}

	@Test
	public void add_plan_should_create_a_new_plan() {
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

		HttpEntity<PagedResources<AddressPlanResource>> respList = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(respList);
		Assert.assertNotNull(respList.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(respList.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(1, plans.size());
	}

	@Test
	public void update_plan_shouldnt_create_a_new_plan() {
	    add_plan_should_create_a_new_plan();
	    
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

		HttpEntity<PagedResources<AddressPlanResource>> respList = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(respList);
		Assert.assertNotNull(respList.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(respList.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(1, plans.size());
	}

	@Test
	public void add_plan_twice_should_create_a_new_plan() {
	    add_plan_should_create_a_new_plan();
	    
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

		HttpEntity<PagedResources<AddressPlanResource>> respList = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(respList);
		Assert.assertNotNull(respList.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(respList.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(2, plans.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_page() {
	    add_plan_twice_should_create_a_new_plan();
	    
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, new PageRequest(0, 1), new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(1, plans.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_null_page() {
        add_plan_twice_should_create_a_new_plan();
        
		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(2, plans.size());
	}

	@Test
	public void get_plan_should_return_second_plan() {
        add_plan_twice_should_create_a_new_plan();
        
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
	public void delete_plan_should_be_absent() {
        add_plan_twice_should_create_a_new_plan();
        
		this.controller.deleteAddressPlan(infraId, id2);

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
	public void delete_plan_should_work() {
	    add_plan_should_create_a_new_plan();
	    
		this.controller.deleteAddressPlan(infraId, id);

		HttpEntity<PagedResources<AddressPlanResource>> resp = this.controller.getAddressPlans(infraId, null, new PagedResourcesAssembler<AddressPlan>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<AddressPlanResource> plans = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(plans);
		Assert.assertEquals(0, plans.size());
	}

	@After
	public void delete_infra_should_work() {
	    this.repository.deleteAll();
	    
		this.infrastructureController.deleteInfrastructure(infraId);

		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
