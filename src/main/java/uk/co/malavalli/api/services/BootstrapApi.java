package uk.co.malavalli.api.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import uk.co.malavalli.classloader.utils.ClasspathResourceLoader;

@Path("/bs")
public class BootstrapApi {

	@Autowired
	@Qualifier("htmlLoader")
	private ClasspathResourceLoader htmlLoader;

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Descriptions({
			@Description(value = "", target = DocTarget.METHOD)
	})
	public Response getBootstrapHtml(@Context Request request) throws Exception {
		String html = htmlLoader.load("bootstrap");
		return Response.ok(html, MediaType.TEXT_HTML).build();

	}

}
