package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.DeleteMode;
import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.dao.AddressRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Address;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.ui.AddressResource;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.domain.model.ui.SubnetResource;
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
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddessControllerTest {

	private static long id, id2;
	private static int infraId;
	private static long subnetId, subnetId2;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private SubnetController subnetController;
	
	@Autowired
	private AddressController controller;
    
    @Autowired
    private AddressRepository repository;

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

		Subnet subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xD0A80000L);
		subnet.setSize(2048L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		HttpEntity<SubnetResource> respSub = this.subnetController.addSubnet(infraId, subnet);
		Assert.assertNotNull(respSub);
		Assert.assertNotNull(respSub.getBody());
		SubnetResource subnetReturned = respSub.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getSubnetId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		subnetId = subnetReturned.getSubnetId();

		subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC0A80000L);
		subnet.setSize(4L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		respSub = this.subnetController.addSubnet(infraId, subnet);
		Assert.assertNotNull(respSub);
		Assert.assertNotNull(respSub.getBody());
		subnetReturned = respSub.getBody();
		Assert.assertNotNull(subnetReturned);
		Assert.assertNotNull(subnetReturned.getSubnetId());
		Assert.assertEquals(subnet.getDescription(), subnetReturned.getDescription());
		Assert.assertEquals(subnet.getInfraId(), subnetReturned.getInfraId());
		Assert.assertEquals(subnet.getIp(), subnetReturned.getIp());
		Assert.assertEquals(subnet.getGroup(), subnetReturned.getGroup());
		Assert.assertEquals(subnet.getSize(), subnetReturned.getSize());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		subnetId2 = subnetReturned.getSubnetId();
		
		HttpEntity<PagedResources<SubnetResource>> respSubs = this.subnetController.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(respSubs);
		Assert.assertNotNull(respSubs.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(respSubs.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(2, subnets.size());
	}

	@Test
	public void get_all_should_return_empty_array() {
		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertTrue(addresses.isEmpty());
	}

	@Test
	public void add_address_should_create_a_new_address() {
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId);
		address.setInfraId(infraId);
		address.setLocation("somewhere");
		address.setIp(0xD0A80001L);
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		HttpEntity<AddressResource> resp = this.controller.addAddress(infraId, subnetId, address);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource addressReturned = resp.getBody();
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
		Assert.assertEquals(3, addressReturned.getLinks().size());
		id = addressReturned.getIp();
	}

	@Test
	public void add_address_should_create_a_new_address_second_subnet() {
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId2);
		address.setInfraId(infraId);
		address.setLocation("somewhere");
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		HttpEntity<AddressResource> resp = this.controller.addAddress(infraId, subnetId2, address);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource addressReturned = resp.getBody();
		Assert.assertNotNull(addressReturned);
		Assert.assertNotNull(addressReturned.getIp());
		Assert.assertEquals(address.getDescription(), addressReturned.getDescription());
		Assert.assertEquals(address.getSubnetId(), addressReturned.getSubnetId());
		Assert.assertEquals(new Long(0xC0A80001L), addressReturned.getIp());
		Assert.assertEquals(address.getLocation(), addressReturned.getLocation());
		Assert.assertEquals(address.getMac(), addressReturned.getMac());
		Assert.assertEquals(address.getLastModifed(), addressReturned.getLastModifed());
		Assert.assertEquals(address.getLastPol(), addressReturned.getLastPol());
		Assert.assertEquals(address.getName(), addressReturned.getName());
		Assert.assertEquals(address.getPhone(), addressReturned.getPhone());
		Assert.assertEquals(address.getUserId(), addressReturned.getUserId());
		Assert.assertEquals(address.getUserInfo(), addressReturned.getUserInfo());
		Assert.assertEquals(3, addressReturned.getLinks().size());
	}

	@Test
	public void add_address_should_create_a_new_address_second_subnet2() {
	    add_address_should_create_a_new_address_second_subnet();
	    
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId2);
		address.setInfraId(infraId);
		address.setLocation("somewhere");
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		HttpEntity<AddressResource> resp = this.controller.addAddress(infraId, subnetId2, address);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource addressReturned = resp.getBody();
		Assert.assertNotNull(addressReturned);
		Assert.assertNotNull(addressReturned.getIp());
		Assert.assertEquals(address.getDescription(), addressReturned.getDescription());
		Assert.assertEquals(address.getSubnetId(), addressReturned.getSubnetId());
		Assert.assertEquals(new Long(0xC0A80002L), addressReturned.getIp());
		Assert.assertEquals(address.getLocation(), addressReturned.getLocation());
		Assert.assertEquals(address.getMac(), addressReturned.getMac());
		Assert.assertEquals(address.getLastModifed(), addressReturned.getLastModifed());
		Assert.assertEquals(address.getLastPol(), addressReturned.getLastPol());
		Assert.assertEquals(address.getName(), addressReturned.getName());
		Assert.assertEquals(address.getPhone(), addressReturned.getPhone());
		Assert.assertEquals(address.getUserId(), addressReturned.getUserId());
		Assert.assertEquals(address.getUserInfo(), addressReturned.getUserInfo());
		Assert.assertEquals(3, addressReturned.getLinks().size());
	}

	@Test
	public void add_existing_address_should_return_a_conflict_error() {
	    add_address_should_create_a_new_address_second_subnet2();
	    
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId2);
		address.setInfraId(infraId);
		address.setIp(0xC0A80002L);
		address.setLocation("somewhere");
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		try {
			this.controller.addAddress(infraId, subnetId2, address);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.IP_CONFLICT, e.getError());
		}
	}

	@Test
	public void add_address_should_return_a_full_subnet_error() {
	    add_address_should_create_a_new_address_second_subnet2();
	    
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId2);
		address.setInfraId(infraId);
		address.setLocation("somewhere");
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		try {
			this.controller.addAddress(infraId, subnetId2, address);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_FULL, e.getError());
		}
	}

	@Test
	public void add_bad_address_should_return_a_not_in_subnet_error() {
		Address address = new Address();
		address.setDescription("Test description");
		address.setSubnetId(subnetId);
		address.setInfraId(infraId);
		address.setIp(0xC0A80000L);
		address.setLocation("somewhere");
		address.setMac("000000000000");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 01");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		try {
			this.controller.addAddress(infraId, subnetId, address);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.IP_NOT_IN_SUBNET, e.getError());
		}
	}

	@Test
	public void get_all_should_return_an_array_with_one_elem() {
	    add_address_should_create_a_new_address();
	    
		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(1, addresses.size());
	}

	@Test
	public void update_address_shouldnt_create_a_new_address() {
	    add_address_should_create_a_new_address();
	    
		Address address = new Address();
		address.setDescription("Test description 2");
		address.setInfraId(infraId);
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
		HttpEntity<AddressResource> resp = this.controller.updateAddress(infraId, subnetId, id, address);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource addressReturned = resp.getBody();
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
		Assert.assertEquals(3, addressReturned.getLinks().size());

		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(1, addresses.size());
	}

	@Test
	public void add_address_twice_should_create_a_new_address() {
	    add_address_should_create_a_new_address();
	    
		Address address = new Address();
		address.setDescription("Test description 3");
		address.setInfraId(infraId);
		address.setSubnetId(subnetId);
		address.setLocation("somewhere");
		address.setIp(0xD0A80101L);
		address.setMac("000000000100");
		address.setLastModifed(new Date());
		address.setLastPol(new Date());
		address.setName("My Server 02");
		address.setPhone("0000000000");
		address.setUserId("user");
		address.setUserInfo("My user");
		HttpEntity<AddressResource> resp = this.controller.addAddress(infraId, subnetId, address);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource addressReturned = resp.getBody();
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
		Assert.assertEquals(3, addressReturned.getLinks().size());
		id2 = addressReturned.getIp();
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem() {
	    add_address_twice_should_create_a_new_address();
	    
		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(2, addresses.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_page() {
        add_address_twice_should_create_a_new_address();
        
		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, new PageRequest(0, 1), new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(1, addresses.size());
	}

	@Test
	public void get_free_should_return_an_array_with_two_elem_with_null_page() {
        add_address_twice_should_create_a_new_address();
        
		PagedResources<AddressResource> resources = this.controller.getAddresses(RequestMode.FREE, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null));
		List<AddressResource> addresses = IterableUtils.toList(resources.getContent());
		Assert.assertNotNull(addresses);
		Assert.assertEquals(1024, addresses.size());
		Assert.assertEquals(0, resources.getMetadata().getNumber());
		Assert.assertEquals(2, resources.getMetadata().getTotalPages());
		Assert.assertEquals(2044L, resources.getMetadata().getTotalElements());
	}

	@Test
	public void get_free_should_return_an_array_with_two_elem_with_second_page() {
        add_address_twice_should_create_a_new_address();
        
		PagedResources<AddressResource> resources = this.controller.getAddresses(RequestMode.FREE, infraId, subnetId, new PageRequest(2, 512), new PagedResourcesAssembler<Address>(resolver, null));
		List<AddressResource> addresses = IterableUtils.toList(resources.getContent());
		Assert.assertNotNull(addresses);
		Assert.assertEquals(512, addresses.size());
		Assert.assertEquals(2, resources.getMetadata().getNumber());
		Assert.assertEquals(4, resources.getMetadata().getTotalPages());
		Assert.assertEquals(2044L, resources.getMetadata().getTotalElements());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_null_page() {
        add_address_twice_should_create_a_new_address();
        
		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(2, addresses.size());
	}

	@Test
	public void get_next_free_should_return_a_free_address() {
        add_address_twice_should_create_a_new_address();
        
		HttpEntity<AddressResource> resp = this.controller.getFreeAddress(infraId, subnetId);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource address = resp.getBody();
		Assert.assertNotNull(address);
		Assert.assertEquals(true, address.isFree());
		Assert.assertEquals(0xD0A80002L, (long) address.getIp());
		Assert.assertEquals(3, address.getLinks().size());
	}

	@Test
	public void get_next_free_should_return_not_found() {
	    add_address_should_create_a_new_address_second_subnet2();
	    
		try {
			this.controller.getFreeAddress(infraId, subnetId2);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_FULL, e.getError());
		}
	}

	@Test
	public void get_address_should_return_second_address() {
        add_address_twice_should_create_a_new_address();
        
		HttpEntity<AddressResource> resp = this.controller.getAddress(infraId, subnetId, id2);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		AddressResource address = resp.getBody();
		Assert.assertNotNull(address);
		Assert.assertEquals("Test description 3", address.getDescription());
		Assert.assertEquals(0xD0A80101L, (long) address.getIp());
		Assert.assertEquals(3, address.getLinks().size());
	}

	@Test
	public void get_full_subnet_should_return_second_subnet() {
        add_address_twice_should_create_a_new_address();
        
		HttpEntity<SubnetResource> resp = this.subnetController.getSubnet(infraId, subnetId, RequestMode.FULL);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		SubnetResource subnet = resp.getBody();
		Assert.assertNotNull(subnet);
		Assert.assertNotNull(subnet.getAddresses());
		Assert.assertEquals(2, subnet.getAddresses().count());
	}

	@Test
	public void delete_address_should_be_absent() {
        add_address_twice_should_create_a_new_address();
        
		this.controller.deleteAddress(infraId, subnetId, id2);
		
		try {
			this.controller.getAddress(infraId, subnetId, id2);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.IP_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void view_get_view_all_should_return_a_model_view() {
		ModelAndView view = this.controller.getAddressesView(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("addresses", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
	    add_address_twice_should_create_a_new_address();
	    
		ModelAndView view = this.controller.getAddressView(infraId, subnetId, id);
		Assert.assertNotNull(view);
		Assert.assertEquals("address", view.getViewName());
	}

	@Test
	public void delete_address_should_work() {
        add_address_should_create_a_new_address();
        
		this.controller.deleteAddress(infraId, subnetId, id);

		List<AddressResource> addresses = IterableUtils.toList(this.controller.getAddresses(null, infraId, subnetId, null, new PagedResourcesAssembler<Address>(resolver, null)));
		Assert.assertNotNull(addresses);
		Assert.assertEquals(0, addresses.size());
	}

	@Test
	public void delete_subnet_should_work() {
	    delete_address_should_work();
	    
		this.subnetController.deleteSubnet(infraId, subnetId, null);

		HttpEntity<PagedResources<SubnetResource>> resp = this.subnetController.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void delete_non_empty_subnet_should_fail() {
	    add_address_should_create_a_new_address_second_subnet2();
	    
		try {
			this.subnetController.deleteSubnet(infraId, subnetId2, null);Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_NOT_EMPTY, e.getError());
		}
	}

	@Test
	public void delete_subnet_full_mode_should_work() {
	    add_address_should_create_a_new_address_second_subnet2();
	    
		this.subnetController.deleteSubnet(infraId, subnetId2, DeleteMode.FULL);
	}

	@After
	public void delete_infra_should_work() {
	    this.repository.deleteAll();

        this.subnetController.deleteSubnet(infraId, subnetId, DeleteMode.FULL);
        this.subnetController.deleteSubnet(infraId, subnetId2, DeleteMode.FULL);
        
		this.infrastructureController.deleteInfrastructure(infraId);

		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
