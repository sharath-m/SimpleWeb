package uk.co.malavalli.api;

import static com.jayway.restassured.RestAssured.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.RestAssured;

import uk.co.malavalli.server.IntegrationTestServer;

@ContextConfiguration(locations = { "classpath:application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PingTest {

	Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	IntegrationTestServer integrationTestServer;

	@Before
	public void before() throws Exception {
		RestAssured.port = 8989;
		integrationTestServer.start();
	}

	@After
	public void after() throws Exception {
		integrationTestServer.stop();
	}

	@Test
	public void testname() throws Exception {
		String asString = get("/ping").asString();
		LOG.info(asString);
	}
}
