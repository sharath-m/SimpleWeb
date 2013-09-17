package uk.co.malavalli.api;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import uk.co.malavalli.server.IntegrationTestServer;

public class ApiTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		IntegrationTestServer integrationTestServer = testContext.getApplicationContext().getBean(IntegrationTestServer.class);
		integrationTestServer.start();
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		IntegrationTestServer integrationTestServer = testContext.getApplicationContext().getBean(IntegrationTestServer.class);
		integrationTestServer.stop();
	}

}
