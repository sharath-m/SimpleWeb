package uk.co.malavalli.api.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import uk.co.malavalli.services.user.UserInfo;
import uk.co.malavalli.services.user.UserService;

@Path("/user")
public class UserApi {

	private UserService userService;

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Descriptions({
			@Description(value = "", target = DocTarget.METHOD)
	})
	public Response getAllUsers(@Context Request request) throws Exception {
		List<UserInfo> allUsers = userService.getAllUsers();
		return Response.ok(allUsers.toString(), MediaType.TEXT_PLAIN).build();

	}

	@Path("/add/username/{userName}")
	@POST
	@Consumes({ MediaType.TEXT_PLAIN })
	@Descriptions({
			@Description(value = "", target = DocTarget.METHOD)
	})
	public Response addUser(@Context Request request,
			@Description("User Name") @PathParam("userName") final String userName)
			throws Exception {
		userService.addUser(userName);
		return Response.ok(String.format("User %s added", userName)).build();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
