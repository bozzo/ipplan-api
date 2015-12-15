package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Subnet;
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
public class SubnetControllerTests {

	private static long id, id2;
	private static int infraId;
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private SubnetController controller;

	@Test
	public void a_add_infra_should_create_a_new_infra() {
		Infrastructure infra = new Infrastructure();
		infra.setDescription("Test description");
		infra.setGroup("group");
		Infrastructure infraReturned = this.infrastructureController.addInfrastructure(infra);
		Assert.assertNotNull(infraReturned);
		Assert.assertNotNull(infraReturned.getId());
		Assert.assertEquals(infra.getDescription(), infraReturned.getDescription());
		Assert.assertEquals(infra.getCrm(), infraReturned.getCrm());
		Assert.assertEquals(infra.getGroup(), infraReturned.getGroup());
		infraId = infraReturned.getId();
	}

	@Test
	public void b_get_all_should_return_an_infra_array_with_one_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures());
		Assert.assertNotNull(infras);
		Assert.assertEquals(1, infras.size());
	}

	@Test
	public void c_get_all_should_return_empty_array() {
		List<Subnet> subnets = IterableUtils.toList(this.controller.getSubnets(infraId));
		Assert.assertNotNull(subnets);
		Assert.assertTrue(subnets.isEmpty());
	}

	@Test
	public void d_add_subnet_should_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC0A80001L);
		subnet.setSize(65535L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		Subnet subnetReturned = this.controller.addSubnet(infraId, subnet);
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
		Assert.assertEquals(subnet, subnetReturned);
		id = subnetReturned.getId();
	}

	@Test
	public void e_get_all_should_return_an_array_with_one_elem() {
		List<Subnet> subnets = IterableUtils.toList(this.controller.getSubnets(infraId));
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void f_update_subnet_shouldnt_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 2");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC0A80101L);
		subnet.setSize(65535L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		subnet.setId(id);
		Subnet subnetReturned = this.controller.updateSubnet(infraId, id, subnet);
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
		Assert.assertEquals(subnet, subnetReturned);
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		List<Subnet> zones = IterableUtils.toList(this.controller.getSubnets(infraId));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void h_add_subnet_shouldnt_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description 3");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC1A8000L);
		subnet.setSize(65535L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		Subnet subnetReturned = this.controller.addSubnet(infraId, subnet);
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
		Assert.assertEquals(subnet, subnetReturned);
		id2 = subnetReturned.getId();
	}

	@Test
	public void i_get_all_should_return_an_array_with_two_elem() {
		List<Subnet> subnets = IterableUtils.toList(this.controller.getSubnets(infraId));
		Assert.assertNotNull(subnets);
		Assert.assertEquals(2, subnets.size());
	}

	@Test
	public void j_get_subnet_should_return_second_subnet() {
		Subnet subnet = this.controller.getSubnet(infraId, id2);
		Assert.assertNotNull(subnet);
		Assert.assertEquals("Test description 3", subnet.getDescription());
		Assert.assertEquals(0xC1A80000L, (long) subnet.getIp());
	}

	@Test
	public void k_delete_subnet_should_work() {
		this.controller.deleteSubnet(infraId, id2);
	}

	@Test
	public void l_get_subnet_shouldnt_return_subnet() {
		Subnet zone = this.controller.getSubnet(infraId, id2);
		Assert.assertNull(zone);
	}

	@Test
	public void m_delete_subnet_should_work() {
		this.controller.deleteSubnet(infraId, id);
	}

	@Test
	public void n_get_all_should_return_an_array_with_no_elem() {
		List<Subnet> zones = IterableUtils.toList(this.controller.getSubnets(infraId));
		Assert.assertNotNull(zones);
		Assert.assertEquals(0, zones.size());
	}

	@Test
	public void o_delete_infra_should_work() {
		this.infrastructureController.deleteInfrastructure(infraId);
	}

	@Test
	public void p_get_all_should_return_an_array_with_two_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures());
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
