package dsk.sample.oauth;

import org.glassfish.jersey.client.oauth2.ClientIdentifier;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;

/**
 * 共通の変数置き場。 ※サンプルのための仕組みです。
 */
public final class Shared {

	/**
	 * APIのId。
	 */
	private static final ClientIdentifier CLIENT_IDENTIFIER;

	/**
	 * OAuth2のフロー。
	 */
	private static final OAuth2CodeGrantFlow FLOW;

	static {
		//  Key, Secretを設定する。
		CLIENT_IDENTIFIER = new ClientIdentifier(
				getEnv("GITHUB_CLIENT_ID"),
				getEnv("GITHUB_CLIENT_SECRET"));
		// githubのフローを構築する。
		FLOW = OAuth2ClientSupport
				.authorizationCodeGrantFlowBuilder(CLIENT_IDENTIFIER,
						"https://github.com/login/oauth/authorize",
						"https://github.com/login/oauth/access_token")
				// githubで設定したcallbackのURL。
				.redirectUri("http://localhost:8080/sample-oauth/callback")
				.scope("user")
				.build();
	}

	/**
	 * githubのOAuthフローを取得する。
	 *
	 * @return OAuthのフロー。
	 */
	public static OAuth2CodeGrantFlow getGithubFlow() {
		return FLOW;
	}

	/**
	 * 環境変数から値を取得する。
	 *
	 * @param key 環境変数のキー。
	 * @return 環境変数の値。
	 */
	private static String getEnv(final String key) {
		//OSによって取得の仕方が違うので2段階でとる。
		String value = System.getenv(key);
		if (value == null) {
			value = System.getProperty(key);
		}
		System.out.printf("key:%s, value:%s\n", key, value);
		return value;
	}
}
