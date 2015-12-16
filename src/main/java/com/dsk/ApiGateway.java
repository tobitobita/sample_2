package com.dsk;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ApiGateway {
	// https://mxj87hz3d6.execute-api.us-west-2.amazonaws.com/test/sampleFunction

	public static void main(String[] args) {
		System.out.println("start");
		// jsonをパースするときの設定。
		final ClientConfig config = new ClientConfig().register(JacksonFeature.class);
		// 設定を用いてclientを構築し、さらにターゲットを構築する。
		final WebTarget target = newClient(config)
				.target("https://mxj87hz3d6.execute-api.us-west-2.amazonaws.com/test")
				// ターゲットからのパス。
				.path("/sampleFunction");
		final Response res = target
				// json形式でリクエスト。
				.request(APPLICATION_JSON)
				// API Gatewayでx-api-keyをつけるという設定をしているのでヘッダーに添付。
				.header("x-api-key", "EVPViXMSeSaKT3OXJgqFyawl4lD4UEUlaHQFM1C4")
				// POSTする。
				.post(json("{\"contents\":\"🍣うまいね。\"}"));
		// レスポンスのボディはjson形式。
		System.out.println(res.readEntity(String.class));
	}
}
