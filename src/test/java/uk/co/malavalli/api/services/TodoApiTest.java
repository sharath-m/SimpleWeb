package uk.co.malavalli.api.services;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import uk.co.malavalli.api.AbstractApiTest;
import uk.co.malavalli.api.services.TodoApi;
import uk.co.malavalli.classloader.utils.ClasspathResourceLoader;

//import static com.jayway.restassured.matcher.RestAssuredMatchers.*;

public class TodoApiTest extends AbstractApiTest {

	private static final String TODO_HTML = "<html><head></head><body><div>TODO</div></body></html>";

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	TodoApi todoApi;

	@Mock
	ClasspathResourceLoader mockResourceLoader;

	@Before
	public void before() {
		initMocks(this);
		setInternalState(todoApi, "htmlLoader", mockResourceLoader);
		when(mockResourceLoader.load(anyString())).thenReturn(TODO_HTML);
	}

	@Test
	@DirtiesContext
	public void getTodoHtml() throws Exception {

		given().
				expect().statusCode(200).
				contentType("text/html").
				body(containsString(TODO_HTML)).
				get("/todo");
		LOG.info(get("/todo").asString());
	}

	@Test
	@DirtiesContext
	public void postTodoForm() throws Exception {
		given().formParam("todoItem", "Task 1").
				expect().statusCode(200).
				contentType("application/x-www-form-urlencoded").
				body(equalTo("1")).
				when().post("/todo");

		LOG.info(given().queryParam("todoItem", "Task 1").post("/todo").asString());
	}
}
