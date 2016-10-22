package org.bozzo.ipplan.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.dao.RangeRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.Subnet;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.domain.model.ui.RangeResource;
import org.bozzo.ipplan.domain.model.ui.SubnetResource;
import org.bozzo.ipplan.domain.model.ui.ZoneResource;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RangeControllerTest {

	private static long id, id2;
	private static int infraId;
	private static long zoneId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private ZoneController zoneController;
	
	@Autowired
	private RangeController controller;
	
	@Autowired
	private SubnetController subnetController;
	
	@Autowired 
	private WebApplicationContext wac; 
	
    @Autowired 
    private MockHttpSession session;
    
    @Autowired 
    private RangeRepository repository;

    private MockMvc mockMvc;
	private Long subnetId;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

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

		Zone zone = new Zone();
		zone.setDescription("Test description");
		zone.setInfraId(infraId);
		zone.setIp(0xC0A80001L);
		HttpEntity<ZoneResource> respZone = this.zoneController.addZone(infraId, zone);
		Assert.assertNotNull(respZone);
		Assert.assertNotNull(respZone.getBody());
		ZoneResource zoneReturned = respZone.getBody();
		Assert.assertNotNull(zoneReturned);
		Assert.assertNotNull(zoneReturned.getId());
		Assert.assertEquals(zone.getDescription(), zoneReturned.getDescription());
		Assert.assertEquals(zone.getInfraId(), zoneReturned.getInfraId());
		Assert.assertEquals(zone.getIp(), zoneReturned.getIp());
		zoneId = zoneReturned.getZoneId();

		List<ZoneResource> zones = IterableUtils.toList(this.zoneController.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void get_all_should_return_empty_array() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertTrue(ranges.isEmpty());
	}

	@Test
	public void add_range_should_create_a_new_range() {
		Range range = new Range();
		range.setDescription("Test description");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(65536L);
		range.setIp(0xC0A80000L);
		HttpEntity<RangeResource> resp = this.controller.addRange(infraId, zoneId, range);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		RangeResource rangeReturned = resp.getBody();
		Assert.assertNotNull(rangeReturned);
		Assert.assertNotNull(rangeReturned.getId());
		Assert.assertEquals(range.getDescription(), rangeReturned.getDescription());
		Assert.assertEquals(range.getInfraId(), rangeReturned.getInfraId());
		Assert.assertEquals(range.getZoneId(), rangeReturned.getZoneId());
		Assert.assertEquals(range.getIp(), rangeReturned.getIp());
		Assert.assertEquals(range.getSize(), rangeReturned.getSize());
		Assert.assertEquals(4, rangeReturned.getLinks().size());
		id = rangeReturned.getRangeId();

		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void update_range_shouldnt_create_a_new_range() {
	    add_range_should_create_a_new_range();
	    
		Range range = new Range();
		range.setDescription("Test description 2");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(131072L);
		range.setIp(0xC0A80000L);
		HttpEntity<RangeResource> resp = this.controller.updateRange(infraId, zoneId, id, range);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		RangeResource rangeReturned = resp.getBody();
		Assert.assertNotNull(rangeReturned);
		Assert.assertNotNull(rangeReturned.getId());
		Assert.assertEquals(range.getDescription(), rangeReturned.getDescription());
		Assert.assertEquals(range.getInfraId(), rangeReturned.getInfraId());
		Assert.assertEquals(range.getZoneId(), rangeReturned.getZoneId());
		Assert.assertEquals(range.getIp(), rangeReturned.getIp());
		Assert.assertEquals(range.getSize(), rangeReturned.getSize());
		Assert.assertEquals(4, rangeReturned.getLinks().size());
	}

	@Test
	public void update_range_should_should_return_zone_not_found() {
	    update_range_shouldnt_create_a_new_range();
	    
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(12L);
		range.setId(id);
		range.setSize(65536L);
		range.setIp(0xC0A90000L);
		try {
			this.controller.updateRange(infraId, 12L, id, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.ZONE_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void get_all_should_return_an_array_with_one_elem() {
	    add_range_should_create_a_new_range();
	    
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void add_range_twice_should_create_a_new_range() {
	    update_range_shouldnt_create_a_new_range();
	    
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(131072L);
		range.setIp(0xD0A80000L);
		HttpEntity<RangeResource> resp = this.controller.addRange(infraId, zoneId, range);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		RangeResource rangeReturned = resp.getBody();
		Assert.assertNotNull(rangeReturned);
		Assert.assertNotNull(rangeReturned.getId());
		Assert.assertEquals(range.getDescription(), rangeReturned.getDescription());
		Assert.assertEquals(range.getInfraId(), rangeReturned.getInfraId());
		Assert.assertEquals(range.getZoneId(), rangeReturned.getZoneId());
		Assert.assertEquals(range.getIp(), rangeReturned.getIp());
		Assert.assertEquals(range.getSize(), rangeReturned.getSize());
		Assert.assertEquals(4, rangeReturned.getLinks().size());
		id2 = rangeReturned.getRangeId();
	}

	@Test
	public void add_range_should_should_return_zone_not_found() {
	    add_range_twice_should_create_a_new_range();
	    
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(12L);
		range.setSize(65536L);
		range.setIp(0xD0A90000L);
		try {
			this.controller.addRange(infraId, 12L, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.ZONE_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void add_range_should_return_range_conflict() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(131072L);
		range.setIp(0xD0A80000L);
		try {
			this.controller.addRange(infraId, zoneId, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_CONFLICT, e.getError());
		}
	}

	@Test
	public void add_range_should_return_range_conflict2() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(512L);
		range.setIp(0xD0A80200L);
		range.setId(12L);
		try {
			this.controller.addRange(infraId, zoneId, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_CONFLICT, e.getError());
		}
	}

	@Test
	public void add_range_should_return_range_conflict3() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(1L << 24);
		range.setIp(0xD0000000L);
		try {
			this.controller.addRange(infraId, zoneId, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_CONFLICT, e.getError());
		}
	}

	@Test
	public void add_range_should_return_bad_network() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(1L << 24);
		range.setIp(0xD0000001L);
		try {
			this.controller.addRange(infraId, zoneId, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETWORK, e.getError());
		}
	}

	@Test
	public void add_range_should_return_bad_netmask() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(1L + (1L << 24));
		range.setIp(0xD0000000L);
		try {
			this.controller.addRange(infraId, zoneId, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETMASK, e.getError());
		}
	}

	@Test
	public void update_range_should_return_range_conflict() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(131072L);
		range.setIp(0xD0A80000L);
		try {
			this.controller.updateRange(infraId, zoneId, id, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_CONFLICT, e.getError());
		}
	}

	@Test
	public void update_range_should_return_range_conflict2() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(512L);
		range.setIp(0xD0A80200L);
		try {
			this.controller.updateRange(infraId, zoneId, id, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_CONFLICT, e.getError());
		}
	}

	@Test
	public void update_range_should_return_range_conflict3() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(1L << 24);
		range.setIp(0xD0000000L);
		try {
			this.controller.updateRange(infraId, zoneId, id, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_CONFLICT, e.getError());
		}
	}

	@Test
	public void update_range_should_return_bad_network() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(1L << 24);
		range.setIp(0xD0000001L);
		try {
			this.controller.updateRange(infraId, zoneId, id, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETWORK, e.getError());
		}
	}

	@Test
	public void update_range_should_return_bad_netmask() {
        add_range_twice_should_create_a_new_range();
        
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(1L + (1L << 24));
		range.setIp(0xD0000000L);
		try {
			this.controller.updateRange(infraId, zoneId, id, range);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.BAD_NETMASK, e.getError());
		}
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem() {
        add_range_twice_should_create_a_new_range();
        
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(2, ranges.size());
	}

	@Test
	public void get_all_should_return_an_array_with_one_elem_with_page() {
        add_range_twice_should_create_a_new_range();
        
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, new PageRequest(0, 1), new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_null_page() {
        add_range_twice_should_create_a_new_range();
        
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(2, ranges.size());
	}

	@Test
	public void get_range_should_return_second_range() {
        add_range_twice_should_create_a_new_range();
        
		HttpEntity<RangeResource> resp = this.controller.getRange(infraId, zoneId, id2, RequestMode.RESERVED);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		RangeResource range = resp.getBody();
		Assert.assertEquals("Test description 3", range.getDescription());
		Assert.assertEquals(0xD0A80000L, (long) range.getIp());
		Assert.assertEquals(4, range.getLinks().size());
	}

	@Test
	public void get_full_zone_should_return_second_zone() {
        add_range_twice_should_create_a_new_range();
        
		HttpEntity<ZoneResource> resp = this.zoneController.getZone(infraId, zoneId, RequestMode.FULL);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zone = resp.getBody();
		Assert.assertNotNull(zone);
		Assert.assertEquals("Test description", zone.getDescription());
		Assert.assertEquals(0xC0A80001L, (long) zone.getIp());
		Assert.assertEquals(3, zone.getLinks().size());
		Assert.assertEquals(2, zone.getRanges().count());
	}

	@Test
	public void delete_range_should_be_absent() {
        add_range_twice_should_create_a_new_range();
        
		this.controller.deleteRange(infraId, zoneId, id2);

		try {
			this.controller.getRange(infraId, zoneId, id2, RequestMode.RESERVED);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_NOT_FOUND, e.getError());
		}
	}
	
	@Test
	public void get_range_shouldnt_return_range_view() throws Exception {
        add_range_twice_should_create_a_new_range();
        
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/infras/"+infraId+"/zones/"+zoneId+"/ranges").session(session)
		        .accept(MediaType.TEXT_HTML_VALUE))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.view().name("ranges"));
	}
	
	@Test
	public void get_range_shouldnt_return_range() throws Exception {
        add_range_twice_should_create_a_new_range();
        
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/infras/"+infraId+"/zones/"+zoneId).param("mode", "FULL").session(session)
		        .accept(MediaType.APPLICATION_JSON_VALUE))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void get_range_should_return_range_with_subnet() {
	    add_range_should_create_a_new_range();
	    
		Subnet subnet = new Subnet();
		subnet.setDescription("Test description");
		subnet.setInfraId(infraId);
		subnet.setGroup("group");
		subnet.setIp(0xC0A80100L);
		subnet.setSize(256L);
		subnet.setLastModifed(new Date());
		subnet.setOptionId(1L);
		subnet.setSwipMod(new Date());
		subnet.setUserId("user");
		HttpEntity<SubnetResource> resp = this.subnetController.addSubnet(infraId, subnet);
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
		subnetId=subnetReturned.getSubnetId();

		HttpEntity<RangeResource> respRange = this.controller.getRange(infraId, zoneId, id, RequestMode.FULL);
		Assert.assertNotNull(respRange);
		Assert.assertNotNull(respRange.getBody());
		RangeResource range = respRange.getBody();
		Assert.assertNotNull(range);
		Assert.assertNotNull(range.getSubnets());
		Assert.assertEquals(1, range.getSubnets().count());
	}

	@Test
	public void view_get_view_all_should_return_a_model_view() {
        add_range_twice_should_create_a_new_range();
        
		ModelAndView view = this.controller.getRangesView(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("ranges", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
        add_range_twice_should_create_a_new_range();
        
		ModelAndView view = this.controller.getRangeView(infraId, zoneId, id, RequestMode.RESERVED);
		Assert.assertNotNull(view);
		Assert.assertEquals("range", view.getViewName());
	}

	@Test
	public void delete_range_should_work() {
        add_range_should_create_a_new_range();
        
		this.controller.deleteRange(infraId, zoneId, id);

		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(0, ranges.size());
	}

	@After
	public void z3_delete_zone_should_work() {
	    this.repository.deleteAll();

        this.subnetController.deleteSubnet(infraId, subnetId, null);
		this.zoneController.deleteZone(infraId, zoneId);

		List<ZoneResource> zones = IterableUtils.toList(this.zoneController.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(0, zones.size());

		this.infrastructureController.deleteInfrastructure(infraId);

		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
