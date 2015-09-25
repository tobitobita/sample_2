package dsk.samplejsonp.api.resource;

import dsk.samplejsonp.entity.Theme;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/theme")
public class ThemeResource {

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Theme theme) {
		System.out.printf("theme:%s\n", theme.getTheme());
		theme.setId(new Date().getTime());
		return Response.ok(theme).build();
	}
}
