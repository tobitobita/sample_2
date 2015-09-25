package dsk.samplejsonp.api;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class Config extends ResourceConfig {

	public Config() {
		int version = 11;
		System.out.printf("Config:SampleJsonp-1.0-SNAPSHOT%d\n", version);
		this.packages("dsk.samplejsonp.api.resource")
				// jsonp
				.register(JacksonFeature.class);
	}
}
