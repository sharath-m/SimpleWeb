package uk.co.malavalli.api.Todo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.co.malavalli.api.AbstractApiTest;
import uk.co.malavalli.classloader.utils.ClasspathResourceLoader;

import static com.jayway.restassured.RestAssured.*;
//import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@ContextConfiguration(locations = { "classpath:application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
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
	public void getTodoHtml() throws Exception {

		given().
				expect().statusCode(200).
				body(containsString(TODO_HTML)).
				get("/todo");
		LOG.info(get("/todo").asString());
	}

	@Test
	public void postTodoForm() throws Exception {
		given().queryParam("todoItem", "Task 1").
				expect().statusCode(200).
				contentType("application/x-www-form-urlencoded").
				body(equalTo("1")).
				when().post("/todo");

		LOG.info(given().queryParam("todoItem", "Task 1").post("/todo").asString());
	}
}
