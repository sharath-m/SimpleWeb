package uk.co.malavalli.api;

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
import org.joda.time.DateTime;

import uk.co.malavalli.common.MimeTypes;

@Path("/ping")
public class PingApi {

	@GET
	@Descriptions({
			@Description(value = "Pings the API, interacting with no external services. Returns the current version of the application. " +
					" Example: https://localhost:8080/SimpleWeb/ping", target = DocTarget.METHOD)
	})
	@Produces({ MimeTypes.PLAIN_TEXT })
	public Response get(@Context Request request) {
		String responseBody = String.format("Ping OK - %s", DateTime.now());
		return Response.ok(responseBody).build();
	}

	@GET
	@Descriptions({
			@Description(value = "", target = DocTarget.METHOD)
	})
	@Produces({ MediaType.TEXT_XML })
	public Response getAsXml(@Context Request request) {
		String responseBody = "<?xml version=\"1.0\"?>" + "<ping> OK - " + DateTime.now() + "</ping>";
		return Response.ok(responseBody).build();
	}
}
