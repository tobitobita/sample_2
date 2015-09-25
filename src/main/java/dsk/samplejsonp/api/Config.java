package dsk.samplejsonp.api;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class Config extends ResourceConfig {

	public Config() {
		System.out.printf("Config\n");
		this.packages("dsk.samplejsonp.api.resource")
				// jsonp
				.register(JacksonFeature.class);
	}
}
