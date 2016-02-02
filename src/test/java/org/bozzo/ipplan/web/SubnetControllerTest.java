package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.domain.model.ui.SubnetResource;
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
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubnetControllerTest {

	private static long id, id2;
	private static int infraId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private SubnetController controller;

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
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertTrue(subnets.isEmpty());
	}

	@Test
	public void d_add_subnet_should_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC0A80000L);
		subnet.setSize(65536L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		HttpEntity<SubnetResource> resp = this.controller.addSubnet(infraId, subnet);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnetReturned = resp.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		Assert.assertEquals(3, subnetReturned.getLinks().size());
		id = subnetReturned.getSubnetId();
	}

	@Test
	public void e_get_all_should_return_an_array_with_one_elem() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void f_update_subnet_shouldnt_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 2");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC0A80100L);
		subnet.setSize(32L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		subnet.setId(id);
		HttpEntity<SubnetResource> resp = this.controller.updateSubnet(infraId, id, subnet);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnetReturned = resp.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		Assert.assertEquals(3, subnetReturned.getLinks().size());
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void h1_add_subnet_shouldnt_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80040L);
		subnet.setSize(64L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		HttpEntity<SubnetResource> resp = this.controller.addSubnet(infraId, subnet);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnetReturned = resp.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		Assert.assertEquals(3, subnetReturned.getLinks().size());
		id2 = subnetReturned.getSubnetId();
	}

	@Test
	public void h2_add_subnet_shouldnt_return_a_subnet_conflict_inside() {
		Subnet subnet = new Subnet();
		subnet.setId(120L);
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80040L);
		subnet.setSize(32L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		try {
			this.controller.addSubnet(infraId, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_CONFLICT, e.getError());
		}
	}

	@Test
	public void h2_add_subnet_shouldnt_return_a_subnet_conflict_before() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80000L);
		subnet.setSize(128L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		try {
			this.controller.addSubnet(infraId, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_CONFLICT, e.getError());
		}
	}

	@Test
	public void h2_add_subnet_shouldnt_return_a_subnet_conflict_outside() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80000L);
		subnet.setSize(256L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		try {
			this.controller.addSubnet(infraId, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_CONFLICT, e.getError());
		}
	}

	@Test
	public void h2_add_subnet_shouldnt_return_a_infra_not_found() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(12);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80000L);
		subnet.setSize(256L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		try {
			this.controller.addSubnet(12, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.INFRA_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void h3_add_subnet_should_create_a_new_subnet_before() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80020L);
		subnet.setSize(32L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		HttpEntity<SubnetResource> resp = this.controller.addSubnet(infraId, subnet);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnetReturned = resp.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		Assert.assertEquals(3, subnetReturned.getLinks().size());
	}

	@Test
	public void h3_add_subnet_should_create_a_new_subnet_after() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A80080L);
		subnet.setSize(128L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		HttpEntity<SubnetResource> resp = this.controller.addSubnet(infraId, subnet);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnetReturned = resp.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		Assert.assertEquals(3, subnetReturned.getLinks().size());
	}

	@Test
	public void h4_update_subnet_should_return_bad_network() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		subnet.setId(id);
		subnet.setSize(1L << 24);
		subnet.setIp(0xD0000001L);
		try {
			this.controller.updateSubnet(infraId, id, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETWORK, e.getError());
		}
	}

	@Test
	public void h5_update_subnet_should_return_bad_netmask() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setId(id);
		subnet.setSize(1L + (1L << 24));
		subnet.setIp(0xD0000000L);
		try {
			this.controller.updateSubnet(infraId, id, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETMASK, e.getError());
		}
	}

	@Test
	public void h6_add_subnet_should_return_bad_network() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSize(1L << 24);
		subnet.setIp(0xD0000001L);
		try {
			this.controller.addSubnet(infraId, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETWORK, e.getError());
		}
	}

	@Test
	public void h7_add_subnet_should_return_bad_netmask() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSize(1L + (1L << 24));
		subnet.setIp(0xD0000000L);
		try {
			this.controller.addSubnet(infraId, subnet);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETMASK, e.getError());
		}
	}

	@Test
	public void i_get_all_should_return_an_array_with_two_elem() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(4, subnets.size());
	}

	@Test
	public void j_get_all_should_return_an_array_with_two_elem_with_page() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, new PageRequest(0, 1), new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void j_get_all_with_search_ip_and_size_should_return_an_array_with_one_elem_with_page() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets("192.168.1.0", 255L, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void j_get_all_with_search_ip_and_short_size_should_return_an_empty_array() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets("192.168.1.0", 16L, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(0, subnets.size());
	}

	@Test
	public void j_get_all_with_search_ip_should_return_an_array_with_one_elem_with_page() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets("192.168.1.0", null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void k_get_all_should_return_an_array_with_two_elem_with_null_page() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(4, subnets.size());
	}

	@Test
	public void l_get_subnet_should_return_second_subnet() {
		HttpEntity<SubnetResource> resp = this.controller.getSubnet(infraId, id2, null);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnet = resp.getBody();
		Assert.assertNotNull(subnet);
		Assert.assertEquals("Test description 3", subnet.getDescription());
		Assert.assertEquals(0xC1A80040L, (long) subnet.getIp());
		Assert.assertEquals(3, subnet.getLinks().size());
	}

	@Test
	public void m_delete_subnet_should_work() {
		this.controller.deleteSubnet(infraId, id2, null);
	}

	@Test
	public void n_get_subnet_shouldnt_return_subnet() {
		try {
			this.controller.getSubnet(infraId, id2, null);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void view_get_view_all_should_return_a_model_view() {
		ModelAndView view = this.controller.getSubnetsView(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("subnets", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
		ModelAndView view = this.controller.getSubnetView(infraId, id, null);
		Assert.assertNotNull(view);
		Assert.assertEquals("subnet", view.getViewName());
	}

	@Test
	public void z1_delete_subnet_should_work() {
		this.controller.deleteSubnet(infraId, id, null);
	}

	@Test
	public void z2_get_all_should_return_an_array_with_no_elem() {
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(2, subnets.size());
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