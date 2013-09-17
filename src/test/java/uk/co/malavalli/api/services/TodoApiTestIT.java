package uk.co.malavalli.api.services;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;

import com.jayway.restassured.RestAssured;

public class TodoApiTestIT {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	@Before
	public void before() throws Exception {
		RestAssured.port = 8080;
	}

	@Test
	@DirtiesContext
	public void getTodoHtml() throws Exception {

		given().
				expect().statusCode(200).
				contentType("text/html").
				// body(containsString(html)).
				get("/SimpleWeb/todo");
		LOG.info(get("/SimpleWeb/todo").asString());
	}

}
