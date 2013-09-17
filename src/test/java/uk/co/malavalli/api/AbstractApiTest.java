package uk.co.malavalli.api;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.jayway.restassured.RestAssured;

@ContextConfiguration(locations = { "classpath:application-context.xml" })
@TestExecutionListeners({ ApiTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractApiTest {

	public AbstractApiTest() {
		super();
		RestAssured.port = 8989;
	}

}