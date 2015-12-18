package org.bozzo.ipplan.web;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
import org.bozzo.ipplan.domain.model.ui.ZoneResource;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneControllerTests {

	private static long id, id2;
	private static int infraId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private ZoneController controller;

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
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertTrue(zones.isEmpty());
	}

	@Test
	public void d_add_zone_should_create_a_new_zone() {
		Zone zone = new Zone();
		zone.setDescription("Test description");
		zone.setInfraId(infraId);
		zone.setIp(0xC0A80001L);
		HttpEntity<ZoneResource> resp = this.controller.addZone(infraId, zone);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zoneReturned = resp.getBody();
		Assert.assertNotNull(zoneReturned);
		Assert.assertNotNull(zoneReturned.getId());
		Assert.assertEquals(zone.getDescription(), zoneReturned.getDescription());
		Assert.assertEquals(zone.getInfraId(), zoneReturned.getInfraId());
		Assert.assertEquals(zone.getIp(), zoneReturned.getIp());
		Assert.assertEquals(3, zoneReturned.getLinks().size());
		id = zoneReturned.getZoneId();
	}

	@Test
	public void e_get_all_should_return_an_array_with_one_elem() {
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void f_update_zone_shouldnt_create_a_new_zone() {
		Zone zone = new Zone();
		zone.setDescription("Test description 2");
		zone.setInfraId(infraId);
		zone.setIp(0xC0A80101L);
		zone.setId(id);
		HttpEntity<ZoneResource> resp = this.controller.updateZone(infraId, id, zone);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zoneReturned = resp.getBody();
		Assert.assertNotNull(zoneReturned);
		Assert.assertNotNull(zoneReturned.getId());
		Assert.assertEquals(zone.getDescription(), zoneReturned.getDescription());
		Assert.assertEquals(zone.getInfraId(), zoneReturned.getInfraId());
		Assert.assertEquals(zone.getIp(), zoneReturned.getIp());
		Assert.assertEquals(3, zoneReturned.getLinks().size());
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void h_add_zone_shouldnt_create_a_new_zone() {
		Zone zone = new Zone();
		zone.setDescription("Test description 3");
		zone.setInfraId(infraId);
		zone.setIp(0xC0A80002L);
		HttpEntity<ZoneResource> resp = this.controller.addZone(infraId, zone);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zoneReturned = resp.getBody();
		Assert.assertNotNull(zoneReturned);
		Assert.assertNotNull(zoneReturned.getId());
		Assert.assertEquals(zone.getDescription(), zoneReturned.getDescription());
		Assert.assertEquals(zone.getInfraId(), zoneReturned.getInfraId());
		Assert.assertEquals(zone.getIp(), zoneReturned.getIp());
		Assert.assertEquals(3, zoneReturned.getLinks().size());
		id2 = zoneReturned.getZoneId();
	}

	@Test
	public void i_get_all_should_return_an_array_with_two_elem() {
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(2, zones.size());
	}

	@Test
	public void j_get_all_should_return_an_array_with_two_elem_with_page() {
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, new PageRequest(0, 1), new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void k_get_all_should_return_an_array_with_two_elem_with_null_page() {
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(2, zones.size());
	}

	@Test
	public void l_get_zone_should_return_second_zone() {
		HttpEntity<ZoneResource> resp = this.controller.getZone(infraId, id2);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zone = resp.getBody();
		Assert.assertNotNull(zone);
		Assert.assertEquals("Test description 3", zone.getDescription());
		Assert.assertEquals(0xC0A80002L, (long) zone.getIp());
		Assert.assertEquals(3, zone.getLinks().size());
	}

	@Test
	public void m_delete_zone_should_work() {
		this.controller.deleteZone(infraId, id2);
	}

	@Test
	public void n_get_zone_shouldnt_return_zone() {
		HttpEntity<ZoneResource> resp = this.controller.getZone(infraId, id2);
		Assert.assertNotNull(resp);
		Assert.assertNull(resp.getBody());
		ZoneResource zone = resp.getBody();
		Assert.assertNull(zone);
	}

	@Test
	public void o_delete_zone_should_work() {
		this.controller.deleteZone(infraId, id);
	}

	@Test
	public void p_get_all_should_return_an_array_with_no_elem() {
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(0, zones.size());
	}

	@Test
	public void q_delete_infra_should_work() {
		this.infrastructureController.deleteInfrastructure(infraId);
	}

	@Test
	public void r_get_all_should_return_an_array_with_two_elem() {
		List<InfrastructureResource> infras = IterableUtils.toList(this.infrastructureController.getInfrastructures(null, null, new PagedResourcesAssembler<Infrastructure>(resolver, null)));
		Assert.assertNotNull(infras);
		Assert.assertEquals(0, infras.size());
	}

}
