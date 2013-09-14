package uk.co.malavalli.api;

import static com.jayway.restassured.RestAssured.get;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingTestIT {

	Logger LOG = LoggerFactory.getLogger(getClass());

	@Test
	public void testname() throws Exception {
		String asString = get("/SimpleWeb/ping").asString();
		LOG.info(asString);
	}
}
