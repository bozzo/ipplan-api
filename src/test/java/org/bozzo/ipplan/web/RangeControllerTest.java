package org.bozzo.ipplan.web;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.domain.model.ui.RangeResource;
import org.bozzo.ipplan.domain.model.ui.ZoneResource;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	private WebApplicationContext wac; 
    @Autowired 
    private MockHttpSession session;
    @Autowired 
    private MockHttpServletRequest request;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

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
	public void c_add_zone_should_create_a_new_zone() {
		Zone zone = new Zone();
		zone.setDescription("Test description");
		zone.setInfraId(infraId);
		zone.setIp(0xC0A80001L);
		HttpEntity<ZoneResource> resp = this.zoneController.addZone(infraId, zone);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zoneReturned = resp.getBody();
		Assert.assertNotNull(zoneReturned);
		Assert.assertNotNull(zoneReturned.getId());
		Assert.assertEquals(zone.getDescription(), zoneReturned.getDescription());
		Assert.assertEquals(zone.getInfraId(), zoneReturned.getInfraId());
		Assert.assertEquals(zone.getIp(), zoneReturned.getIp());
		zoneId = zoneReturned.getZoneId();
	}

	@Test
	public void d_get_all_should_return_an_array_with_one_elem() {
		List<ZoneResource> zones = IterableUtils.toList(this.zoneController.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void e_get_all_should_return_empty_array() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertTrue(ranges.isEmpty());
	}

	@Test
	public void f_add_range_should_create_a_new_range() {
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
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void h1_update_range_shouldnt_create_a_new_range() {
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
	public void h2_update_range_should_should_return_zone_not_found() {
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
	public void i_get_all_should_return_an_array_with_one_elem() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void j01_add_range_shouldnt_create_a_new_range() {
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
	public void j02_add_range_should_should_return_zone_not_found() {
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
	public void j03_add_range_should_return_range_conflict() {
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
	public void j04_add_range_should_return_range_conflict() {
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
	public void j06_add_range_should_return_range_conflict() {
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
	public void j07_add_range_should_return_bad_network() {
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
	public void j08_add_range_should_return_bad_netmask() {
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
	public void j09_update_range_should_return_range_conflict() {
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
	public void j10_update_range_should_return_range_conflict() {
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
	public void j11_update_range_should_return_range_conflict() {
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
	public void j12_update_range_should_return_bad_network() {
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
	public void j13_update_range_should_return_bad_netmask() {
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
	public void k_get_all_should_return_an_array_with_two_elem() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(2, ranges.size());
	}

	@Test
	public void l_get_all_should_return_an_array_with_two_elem_with_page() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, new PageRequest(0, 1), new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void m_get_all_should_return_an_array_with_two_elem_with_null_page() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(2, ranges.size());
	}

	@Test
	public void n_get_range_should_return_second_range() {
		HttpEntity<RangeResource> resp = this.controller.getRange(infraId, zoneId, id2);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		RangeResource range = resp.getBody();
		Assert.assertEquals("Test description 3", range.getDescription());
		Assert.assertEquals(0xD0A80000L, (long) range.getIp());
		Assert.assertEquals(4, range.getLinks().size());
	}

	@Test
	public void n_get_full_zone_should_return_second_zone() {
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
	public void o_delete_range_should_work() {
		this.controller.deleteRange(infraId, zoneId, id2);
	}

	@Test
	public void p_get_range_shouldnt_return_range() {
		try {
			this.controller.getRange(infraId, zoneId, id2);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.RANGE_NOT_FOUND, e.getError());
		}
	}
	
	@Test
	public void q_get_range_shouldnt_return_range() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/infras/"+infraId+"/zones/"+zoneId+"/ranges").session(session)
		        .accept(MediaType.TEXT_HTML_VALUE))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.view().name("ranges"));
	}

	@Test
	public void view_get_view_all_should_return_a_model_view() {
		ModelAndView view = this.controller.getRangesView(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("ranges", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
		ModelAndView view = this.controller.getRangeView(infraId, zoneId, id);
		Assert.assertNotNull(view);
		Assert.assertEquals("range", view.getViewName());
	}

	@Test
	public void z1_delete_range_should_work() {
		this.controller.deleteRange(infraId, zoneId, id);
	}

	@Test
	public void z2_get_all_should_return_an_array_with_no_elem() {
		List<RangeResource> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId, null, new PagedResourcesAssembler<Range>(resolver, null)));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(0, ranges.size());
	}

	@Test
	public void z3_delete_zone_should_work() {
		this.zoneController.deleteZone(infraId, zoneId);
	}

	@Test
	public void z4_get_all_should_return_an_array_with_no_elem() {
		List<ZoneResource> zones = IterableUtils.toList(this.zoneController.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(0, zones.size());
	}

	@Test
	public void z5_delete_infra_should_work() {
		this.infrastructureController.deleteInfrastructure(infraId);
	}

	@Test
	public void z6_get_all_should_return_an_array_with_two_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
