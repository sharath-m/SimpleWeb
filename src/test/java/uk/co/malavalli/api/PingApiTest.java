package uk.co.malavalli.api;

import static com.jayway.restassured.RestAssured.*;
//import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PingApiTest extends AbstractApiTest {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	@Test
	public void ping() throws Exception {
		String asString = get("/ping").asString();
		LOG.info(asString);
	}

	@Test
	public void pingXml() throws Exception {
		LOG.info(given().expect().contentType("text/xml").get("/ping").asString());
		given().expect().statusCode(200).contentType("text/xml").body(containsString("<ping>")).get("/ping");
	}
}
