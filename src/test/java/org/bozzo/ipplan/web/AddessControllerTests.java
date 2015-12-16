package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Address;
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
public class AddessControllerTests {

	private static long id, id2;
	private static int infraId;
	private static long subnetId;
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private SubnetController subnetController;
	
	@Autowired
	private AddressController controller;

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
	public void c_add_subnet_should_create_a_new_subnet() {
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xD0A80001L);
		subnet.setSize(65535L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		Subnet subnetReturned = this.subnetController.addSubnet(infraId, subnet);
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
		subnetId = subnetReturned.getId();
	}

	@Test
	public void d_get_all_should_return_an_array_with_one_elem() {
		List<Subnet> subnets = IterableUtils.toList(this.subnetController.getSubnets(infraId));
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void e_get_all_should_return_empty_array() {
		List<Address> addresses = IterableUtils.toList(this.controller.getAddresses(infraId, subnetId));
		Assert.assertNotNull(addresses);
		Assert.assertTrue(addresses.isEmpty());
	}

	@Test
	public void f_add_address_should_create_a_new_address() {
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId);
		address.setLocation("somewhere");
		address.setIp(0xD0A80001L);
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		Address addressReturned = this.controller.addAddress(infraId, subnetId, address);
		Assert.assertNotNull(addressReturned);
		Assert.assertNotNull(addressReturned.getIp());
		Assert.assertEquals(address.getDescription(), addressReturned.getDescription());
		Assert.assertEquals(address.getSubnetId(), addressReturned.getSubnetId());
		Assert.assertEquals(address.getIp(), addressReturned.getIp());
		Assert.assertEquals(address.getLocation(), addressReturned.getLocation());
		Assert.assertEquals(address.getMac(), addressReturned.getMac());
		Assert.assertEquals(address.getLastModifed(), addressReturned.getLastModifed());
		Assert.assertEquals(address.getLastPol(), addressReturned.getLastPol());
		Assert.assertEquals(address.getName(), addressReturned.getName());
		Assert.assertEquals(address.getPhone(), addressReturned.getPhone());
		Assert.assertEquals(address.getUserId(), addressReturned.getUserId());
		Assert.assertEquals(address.getUserInfo(), addressReturned.getUserInfo());
		Assert.assertEquals(address, addressReturned);
		id = addressReturned.getIp();
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		List<Address> addresses = IterableUtils.toList(this.controller.getAddresses(infraId, subnetId));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(1, addresses.size());
	}

	@Test
	public void h_update_address_shouldnt_create_a_new_address() {
		Address address = new Address();
		address.setDescription("Test description 2");
		address.setSubnetId(subnetId);
		address.setLocation("somewhere");
		address.setIp(0xD0A80001L);
		address.setMac("000000000001");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01 bis");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		Address addressReturned = this.controller.updateAddress(infraId, subnetId, id, address);
		Assert.assertNotNull(addressReturned);
		Assert.assertNotNull(addressReturned.getIp());
		Assert.assertEquals(address.getDescription(), addressReturned.getDescription());
		Assert.assertEquals(address.getSubnetId(), addressReturned.getSubnetId());
		Assert.assertEquals(address.getIp(), addressReturned.getIp());
		Assert.assertEquals(address.getLocation(), addressReturned.getLocation());
		Assert.assertEquals(address.getMac(), addressReturned.getMac());
		Assert.assertEquals(address.getLastModifed(), addressReturned.getLastModifed());
		Assert.assertEquals(address.getLastPol(), addressReturned.getLastPol());
		Assert.assertEquals(address.getName(), addressReturned.getName());
		Assert.assertEquals(address.getPhone(), addressReturned.getPhone());
		Assert.assertEquals(address.getUserId(), addressReturned.getUserId());
		Assert.assertEquals(address.getUserInfo(), addressReturned.getUserInfo());
		Assert.assertEquals(address, addressReturned);
	}

	@Test
	public void i_get_all_should_return_an_array_with_one_elem() {
		List<Address> addresses = IterableUtils.toList(this.controller.getAddresses(infraId, subnetId));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(1, addresses.size());
	}

	@Test
	public void j_add_address_shouldnt_create_a_new_address() {
		Address address = new Address();
		address.setDescription("Test description 3");
		address.setSubnetId(subnetId);
		address.setLocation("somewhere");
		address.setIp(0xD1A80001L);
		address.setMac("000000000100");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 02");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		Address addressReturned = this.controller.addAddress(infraId, subnetId, address);
		Assert.assertNotNull(addressReturned);
		Assert.assertNotNull(addressReturned.getIp());
		Assert.assertEquals(address.getDescription(), addressReturned.getDescription());
		Assert.assertEquals(address.getSubnetId(), addressReturned.getSubnetId());
		Assert.assertEquals(address.getIp(), addressReturned.getIp());
		Assert.assertEquals(address.getLocation(), addressReturned.getLocation());
		Assert.assertEquals(address.getMac(), addressReturned.getMac());
		Assert.assertEquals(address.getLastModifed(), addressReturned.getLastModifed());
		Assert.assertEquals(address.getLastPol(), addressReturned.getLastPol());
		Assert.assertEquals(address.getName(), addressReturned.getName());
		Assert.assertEquals(address.getPhone(), addressReturned.getPhone());
		Assert.assertEquals(address.getUserId(), addressReturned.getUserId());
		Assert.assertEquals(address.getUserInfo(), addressReturned.getUserInfo());
		Assert.assertEquals(address, addressReturned);
		id2 = addressReturned.getIp();
	}

	@Test
	public void k_get_all_should_return_an_array_with_two_elem() {
		List<Address> addresses = IterableUtils.toList(this.controller.getAddresses(infraId, subnetId));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(2, addresses.size());
	}

	@Test
	public void l_get_address_should_return_second_address() {
		Address address = this.controller.getAddress(infraId, subnetId, id2);
		Assert.assertNotNull(address);
		Assert.assertEquals("Test description 3", address.getDescription());
		Assert.assertEquals(0xD1A80001L, (long) address.getIp());
	}

	@Test
	public void m_delete_address_should_work() {
		this.controller.deleteAddress(infraId, subnetId, id2);
	}

	@Test
	public void n_get_address_shouldnt_return_address() {
		Address address = this.controller.getAddress(infraId, subnetId, id2);
		Assert.assertNull(address);
	}

	@Test
	public void o_delete_address_should_work() {
		this.controller.deleteAddress(infraId, subnetId, id);
	}

	@Test
	public void p_get_all_should_return_an_array_with_no_elem() {
		List<Address> addresses = IterableUtils.toList(this.controller.getAddresses(infraId, subnetId));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(0, addresses.size());
	}

	@Test
	public void m_delete_subnet_should_work() {
		this.subnetController.deleteSubnet(infraId, subnetId);
	}

	@Test
	public void n_get_all_should_return_an_array_with_no_elem() {
		List<Subnet> zones = IterableUtils.toList(this.subnetController.getSubnets(infraId));
		Assert.assertNotNull(zones);
		Assert.assertEquals(0, zones.size());
	}

	@Test
	public void s_delete_infra_should_work() {
		this.infrastructureController.deleteInfrastructure(infraId);
	}

	@Test
	public void t_get_all_should_return_an_array_with_two_elem() {
		List<Infrastructure> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures());
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
