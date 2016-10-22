package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.dao.SubnetRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Subnet;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubnetControllerTest {

	private static long id, id2;
	private static int infraId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private SubnetController controller;
    
    @Autowired
    private SubnetRepository repository;
	
	@Autowired 
	private WebApplicationContext wac; 
	
    @Autowired 
    private MockHttpSession session;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

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
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertTrue(subnets.isEmpty());
	}

	@Test
	public void add_subnet_should_create_a_new_subnet() {
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

		// Get
		HttpEntity<PagedResources<SubnetResource>> respList = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(respList);
		Assert.assertNotNull(respList.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(respList.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void update_subnet_shouldnt_create_a_new_subnet() {
	    add_subnet_should_create_a_new_subnet();
	    
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

		// Get
		HttpEntity<PagedResources<SubnetResource>> respList = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(respList);
		Assert.assertNotNull(respList.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(respList.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void add_subnet_shouldnt_create_a_new_subnet() {
	    update_subnet_shouldnt_create_a_new_subnet();
	    
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
	public void add_subnet_shouldnt_return_a_subnet_conflict_inside() {
	    add_subnet_shouldnt_create_a_new_subnet();
	    
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
	public void add_subnet_shouldnt_return_a_subnet_conflict_before() {
	    add_subnet_shouldnt_create_a_new_subnet();
        
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
	public void add_subnet_shouldnt_return_a_subnet_conflict_outside() {
	    add_subnet_shouldnt_create_a_new_subnet();
        
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
	public void add_subnet_shouldnt_return_a_infra_not_found() {
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
	public void add_subnet_should_create_a_new_subnet_before() {
	    add_subnet_shouldnt_create_a_new_subnet();
        
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
	public void add_subnet_should_create_a_new_subnet_after() {
	    add_subnet_should_create_a_new_subnet_before();
        
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
		Assert.assertEquals(new Integer(25), subnetReturned.getNetmask());
		Assert.assertEquals(subnet.getLastModifed(), subnetReturned.getLastModifed());
		Assert.assertEquals(subnet.getOptionId(), subnetReturned.getOptionId());
		Assert.assertEquals(subnet.getSwipMod(), subnetReturned.getSwipMod());
		Assert.assertEquals(subnet.getUserId(), subnetReturned.getUserId());
		Assert.assertEquals(3, subnetReturned.getLinks().size());
	}

	@Test
	public void update_subnet_should_return_bad_network() {
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
	public void update_subnet_should_return_bad_netmask() {
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
	public void add_subnet_should_return_bad_network() {
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
	public void add_subnet_should_return_bad_netmask() {
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
	public void get_all_should_return_an_array_with_two_elem() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(2, subnets.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_page() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, new PageRequest(0, 1), new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void get_all_with_search_ip_and_size_should_return_an_array_with_one_elem_with_page() {
        add_subnet_should_create_a_new_subnet_after();
        
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets("192.168.1.0", 255L, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void get_all_with_search_ip_and_short_size_should_return_an_empty_array() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets("192.168.1.0", 16L, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(0, subnets.size());
	}

	@Test
	public void get_all_with_search_ip_should_return_an_array_with_one_elem_with_page() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets("192.168.1.0", null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(1, subnets.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_null_page() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(2, subnets.size());
	}

	@Test
	public void get_subnet_should_return_second_subnet() {
        add_subnet_shouldnt_create_a_new_subnet();
        
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
	public void get_full_infra_should_return_infra() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		HttpEntity<InfrastructureResource> resp = this.infrastructureController.getInfrastructure(infraId, RequestMode.FULL);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		InfrastructureResource subnet = resp.getBody();
		Assert.assertNotNull(subnet);
		Assert.assertNotNull(subnet.getSubnets());
		Assert.assertEquals(2, subnet.getSubnets().count());
	}

	@Test
	public void delete_subnet_should_be_absent() {
        add_subnet_shouldnt_create_a_new_subnet();
        
		this.controller.deleteSubnet(infraId, id2, null);
		
		try {
			this.controller.getSubnet(infraId, id2, null);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.SUBNET_NOT_FOUND, e.getError());
		}
	}
	
	@Test
	public void get_subnet_shouldnt_return_subnet_view() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/infras/"+infraId+"/subnets").session(session)
		        .accept(MediaType.TEXT_HTML_VALUE))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.view().name("subnets"));
	}
	
	@Test
	public void get_subnet_shouldnt_return_subnet() throws Exception {
        add_subnet_shouldnt_create_a_new_subnet();
        
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/infras/"+infraId+"/subnets/"+id).param("mode", "FULL").session(session)
		        .accept(MediaType.APPLICATION_JSON_VALUE))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void view_get_view_all_should_return_a_model_view() {
		ModelAndView view = this.controller.getSubnetsView(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("subnets", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
	    add_subnet_should_create_a_new_subnet();
	    
		ModelAndView view = this.controller.getSubnetView(infraId, id, null);
		Assert.assertNotNull(view);
		Assert.assertEquals("subnet", view.getViewName());
	}

	@Test
	public void delete_subnet_should_work() {
        add_subnet_should_create_a_new_subnet();
        
		this.controller.deleteSubnet(infraId, id, null);
		
		HttpEntity<PagedResources<SubnetResource>> resp = this.controller.getSubnets(null, null, infraId, null, new PagedResourcesAssembler<Subnet>(resolver, null));
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		List<SubnetResource> subnets = IterableUtils.toList(resp.getBody());
		Assert.assertNotNull(subnets);
		Assert.assertEquals(0, subnets.size());
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
