package org.bozzo.ipplan.web;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.bozzo.ipplan.IpplanApiApplication;
import org.bozzo.ipplan.domain.model.Infrastructure;
import org.bozzo.ipplan.domain.model.Range;
import org.bozzo.ipplan.domain.model.Zone;
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
public class RangeControllerTests {

	private static long id, id2;
	private static int infraId;
	private static long zoneId;
	
	@Autowired
	private InfrastructureController infrastructureController;
	
	@Autowired
	private ZoneController zoneController;
	
	@Autowired
	private RangeController controller;

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
	public void c_add_zone_should_create_a_new_zone() {
		Zone zone = new Zone();
		zone.setDescription("Test description");
		zone.setInfraId(infraId);
		zone.setIp(0xC0A80001L);
		Zone zoneReturned = this.zoneController.addZone(infraId, zone);
		Assert.assertNotNull(zoneReturned);
		Assert.assertNotNull(zoneReturned.getId());
		Assert.assertEquals(zone.getDescription(), zoneReturned.getDescription());
		Assert.assertEquals(zone.getInfraId(), zoneReturned.getInfraId());
		Assert.assertEquals(zone.getIp(), zoneReturned.getIp());
		zoneId = zoneReturned.getId();
	}

	@Test
	public void d_get_all_should_return_an_array_with_one_elem() {
		List<Zone> zones = IterableUtils.toList(this.zoneController.getZones(infraId));
		Assert.assertNotNull(zones);
		Assert.assertEquals(1, zones.size());
	}

	@Test
	public void e_get_all_should_return_empty_array() {
		List<Range> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId));
		Assert.assertNotNull(ranges);
		Assert.assertTrue(ranges.isEmpty());
	}

	@Test
	public void f_add_range_should_create_a_new_range() {
		Range range = new Range();
		range.setDescription("Test description");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(65535L);
		range.setIp(0xC0A80001L);
		Range rangeReturned = this.controller.addRange(infraId, zoneId, range);
		Assert.assertNotNull(rangeReturned);
		Assert.assertNotNull(rangeReturned.getId());
		Assert.assertEquals(range.getDescription(), rangeReturned.getDescription());
		Assert.assertEquals(range.getInfraId(), rangeReturned.getInfraId());
		Assert.assertEquals(range.getZoneId(), rangeReturned.getZoneId());
		Assert.assertEquals(range.getIp(), rangeReturned.getIp());
		Assert.assertEquals(range.getSize(), rangeReturned.getSize());
		Assert.assertEquals(range, rangeReturned);
		id = rangeReturned.getId();
	}

	@Test
	public void g_get_all_should_return_an_array_with_one_elem() {
		List<Range> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void h_update_zone_shouldnt_create_a_new_zone() {
		Range range = new Range();
		range.setDescription("Test description 2");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setId(id);
		range.setSize(65535L);
		range.setIp(0xC0A80001L);
		Range rangeReturned = this.controller.updateRange(infraId, zoneId, id, range);
		Assert.assertNotNull(rangeReturned);
		Assert.assertNotNull(rangeReturned.getId());
		Assert.assertEquals(range.getDescription(), rangeReturned.getDescription());
		Assert.assertEquals(range.getInfraId(), rangeReturned.getInfraId());
		Assert.assertEquals(range.getZoneId(), rangeReturned.getZoneId());
		Assert.assertEquals(range.getIp(), rangeReturned.getIp());
		Assert.assertEquals(range.getSize(), rangeReturned.getSize());
		Assert.assertEquals(range, rangeReturned);
	}

	@Test
	public void i_get_all_should_return_an_array_with_one_elem() {
		List<Range> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(1, ranges.size());
	}

	@Test
	public void j_add_range_shouldnt_create_a_new_range() {
		Range range = new Range();
		range.setDescription("Test description 3");
		range.setInfraId(infraId);
		range.setZoneId(zoneId);
		range.setSize(65535L);
		range.setIp(0xC0A80002L);
		Range rangeReturned = this.controller.addRange(infraId, zoneId, range);
		Assert.assertNotNull(rangeReturned);
		Assert.assertNotNull(rangeReturned.getId());
		Assert.assertEquals(range.getDescription(), rangeReturned.getDescription());
		Assert.assertEquals(range.getInfraId(), rangeReturned.getInfraId());
		Assert.assertEquals(range.getZoneId(), rangeReturned.getZoneId());
		Assert.assertEquals(range.getIp(), rangeReturned.getIp());
		Assert.assertEquals(range.getSize(), rangeReturned.getSize());
		Assert.assertEquals(range, rangeReturned);
		id2 = rangeReturned.getId();
	}

	@Test
	public void k_get_all_should_return_an_array_with_two_elem() {
		List<Range> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(2, ranges.size());
	}

	@Test
	public void l_get_range_should_return_second_range() {
		Range range = this.controller.getRange(infraId, zoneId, id2);
		Assert.assertNotNull(range);
		Assert.assertEquals("Test description 3", range.getDescription());
		Assert.assertEquals(0xC0A80002L, (long) range.getIp());
	}

	@Test
	public void m_delete_range_should_work() {
		this.controller.deleteRange(infraId, zoneId, id2);
	}

	@Test
	public void n_get_range_shouldnt_return_range() {
		Range range = this.controller.getRange(infraId, zoneId, id2);
		Assert.assertNull(range);
	}

	@Test
	public void o_delete_range_should_work() {
		this.controller.deleteRange(infraId, zoneId, id);
	}

	@Test
	public void p_get_all_should_return_an_array_with_no_elem() {
		List<Range> ranges = IterableUtils.toList(this.controller.getRanges(infraId, zoneId));
		Assert.assertNotNull(ranges);
		Assert.assertEquals(0, ranges.size());
	}

	@Test
	public void q_delete_zone_should_work() {
		this.zoneController.deleteZone(infraId, zoneId);
	}

	@Test
	public void r_get_all_should_return_an_array_with_no_elem() {
		List<Zone> zones = IterableUtils.toList(this.zoneController.getZones(infraId));
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