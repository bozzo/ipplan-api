package org.bozzo.ipplan;

import org.bozzo.ipplan.IpplanApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IpplanApiApplication.class)
@WebAppConfiguration
public class IpplanApiApplicationTest {

	@Test
	public void contextLoads() {
	}

}
