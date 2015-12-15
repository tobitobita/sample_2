package com.dsk;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ApiGateway {
	// https://mxj87hz3d6.execute-api.us-west-2.amazonaws.com/test/sampleFunction

	public static void main(String[] args) {
		System.out.println("start");
		// jsonをパースするときの設定。
		ClientConfig config = new ClientConfig().register(JacksonFeature.class);
		// 設定情報を用いてclientを構築し、さらにターゲットを構築する。
		WebTarget target = ClientBuilder.newClient(config).target("https://mxj87hz3d6.execute-api.us-west-2.amazonaws.com/test")
				.path("/sampleFunction");
		Response res = target
				.request(MediaType.APPLICATION_JSON)
				.header("x-api-key", "EVPViXMSeSaKT3OXJgqFyawl4lD4UEUlaHQFM1C4")
				.post(Entity.json("{\"contents\":\"🍣うまいね。\"}"));
		System.out.println(res.readEntity(String.class));
	}
}
