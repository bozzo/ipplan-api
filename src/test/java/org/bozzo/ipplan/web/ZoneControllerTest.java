package org.bozzo.ipplan.web;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.domain.ApiError;
import org.bozzo.ipplan.domain.RequestMode;
import org.bozzo.ipplan.domain.dao.ZoneRepository;
import org.bozzo.ipplan.domain.exception.ApiException;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Zone;
import org.bozzo.ipplan.domain.model.ui.InfrastructureResource;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZoneControllerTest {

	private static long id, id2;
	private static int infraId;
	
	HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private ZoneController controller;
    
    @Autowired
    private ZoneRepository repository;

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
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertTrue(zones.isEmpty());
	}

	@Test
	public void add_zone_should_create_a_new_zone() {
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

		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void update_zone_shouldnt_create_a_new_zone() {
	    add_zone_should_create_a_new_zone();
	    
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

		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void add_zone_twice_should_create_a_new_zone() {
	    add_zone_should_create_a_new_zone();
	    
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
		
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(2, zones.size());
	}

	@Test
	public void get_all_should_return_an_array_with_one_elem_with_page() {
	    add_zone_twice_should_create_a_new_zone();
	    
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, new PageRequest(0, 1), new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void get_all_should_return_an_array_with_two_elem_with_null_page() {
        add_zone_twice_should_create_a_new_zone();
        
		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(2, zones.size());
	}

	@Test
	public void get_zone_should_return_second_zone() {
        add_zone_twice_should_create_a_new_zone();
        
		HttpEntity<ZoneResource> resp = this.controller.getZone(infraId, id2, null);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		ZoneResource zone = resp.getBody();
		Assert.assertNotNull(zone);
		Assert.assertEquals("Test description 3", zone.getDescription());
		Assert.assertEquals(0xC0A80002L, (long) zone.getIp());
		Assert.assertEquals(3, zone.getLinks().size());
	}

	@Test
	public void get_full_infra_should_return_infra() {
        add_zone_twice_should_create_a_new_zone();
        
		HttpEntity<InfrastructureResource> resp = this.infrastructureController.getInfrastructure(infraId, RequestMode.FULL);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getBody());
		InfrastructureResource subnet = resp.getBody();
		Assert.assertNotNull(subnet);
		Assert.assertNotNull(subnet.getZones());
		Assert.assertEquals(2, subnet.getZones().count());
	}

	@Test
	public void delete_zone_should_be_absent() {
        add_zone_twice_should_create_a_new_zone();
        
		this.controller.deleteZone(infraId, id2);

		HttpEntity<ZoneResource> resp;
		try {
			resp = this.controller.getZone(infraId, id2, null);
			Assert.assertNull(resp);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertNotNull(e.getError());
			Assert.assertEquals(ApiError.ZONE_NOT_FOUND, e.getError());
		}
	}

	@Test
	public void view_get_view_all_should_return_a_model_view() {
		ModelAndView view = this.controller.getZonesView(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null));
		Assert.assertNotNull(view);
		Assert.assertEquals("zones", view.getViewName());
	}

	@Test
	public void view_get_view_by_id_should_return_a_model_view() {
        add_zone_twice_should_create_a_new_zone();
        
		ModelAndView view = this.controller.getZoneView(infraId, id, null);
		Assert.assertNotNull(view);
		Assert.assertEquals("zone", view.getViewName());
	}

	@Test
	public void delete_zone_should_work() {
		this.controller.deleteZone(infraId, id);

		List<ZoneResource> zones = IterableUtils.toList(this.controller.getZones(infraId, null, new PagedResourcesAssembler<Zone>(resolver, null)));
		Assert.assertNotNull(zones);
		Assert.assertEquals(0, zones.size());
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
