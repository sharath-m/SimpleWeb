package uk.co.malavalli.api;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.malavalli.server.IntegrationTestServer;

import com.jayway.restassured.RestAssured;

public abstract class AbstractApiTest {

	@Autowired
	IntegrationTestServer integrationTestServer;

	public AbstractApiTest() {
		super();
	}

	@Before
	public void before() throws Exception {
		RestAssured.port = 8989;
		integrationTestServer.start();
	}

	@After
	public void after() throws Exception {
		integrationTestServer.stop();
	}

}