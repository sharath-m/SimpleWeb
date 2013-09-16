package uk.co.malavalli.api.Todo;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import uk.co.malavalli.classloader.utils.ClasspathResourceLoader;

@Path("/todo")
public class TodoApi {

	@Autowired
	@Qualifier("htmlLoader")
	private ClasspathResourceLoader htmlLoader;

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Descriptions({
			@Description(value = "", target = DocTarget.METHOD)
	})
	public Response myTasks(@Context Request request) throws Exception {
		String html = htmlLoader.load("todo");
		return Response.ok(html, MediaType.TEXT_HTML).build();

	}

	@POST
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED })
	@Descriptions({
			@Description(value = "", target = DocTarget.METHOD)
	})
	public Response createTasks(@Context Request request,
			@Description("The provider specific end range parameter (optional)") @FormParam("todoItem") final List<String> todoItems)
			throws Exception {
		if (todoItems == null)
		{
			return Response.status(Status.BAD_REQUEST.getStatusCode()).build();
		}
		return Response.ok(todoItems.size()).build();
	}
}
