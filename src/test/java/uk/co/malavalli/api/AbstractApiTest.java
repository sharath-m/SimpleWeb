package uk.co.malavalli.api;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.malavalli.server.IntegrationTestServer;

import com.jayway.restassured.RestAssured;

public abstract class AbstractApiTest {

	@Autowired
	public IntegrationTestServer integrationTestServer;

	public AbstractApiTest() {
		super();
	}

	@Before
	public void startServer() throws Exception {
		RestAssured.port = 8989;
		integrationTestServer.start();
	}

	@After
	public void stopServer() throws Exception {
		integrationTestServer.stop();
	}

}