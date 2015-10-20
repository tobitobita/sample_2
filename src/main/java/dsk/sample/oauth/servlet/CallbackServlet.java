package dsk.sample.oauth.servlet;

import dsk.sample.oauth.Shared;
import dsk.sample.oauth.entity.GithubUser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.oauth2.TokenResult;
import org.glassfish.jersey.jackson.JacksonFeature;

@WebServlet(name = "Callback", urlPatterns = {"/callback"})
public class CallbackServlet extends HttpServlet {

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// githubでのログイン後、コールバックされる。
		// アクセストークンを取得する。
		final String accessToken = parseAccessToken(request);
		HttpSession session = request.getSession();
		// 取得したアクセストークンをセッションへ保存する。
		session.setAttribute("accessToken", accessToken);
		// アクセストークンを用いて自分自身の情報をgithubから取得する。
		final GithubUser user = getUser(accessToken);
		// 取得したユーザー情報をセッションへ保存する。
		request.getSession().setAttribute("user", user);
		// ユーザー情報を表示するページへリダイレクトする。
		response.sendRedirect("user.jsp");
	}

	/**
	 * githubより、アクセストークンを用いてユーザー情報を取得する。
	 *
	 * @param accessToken アクセストークン。
	 * @return ユーザー情報。
	 */
	private static GithubUser getUser(String accessToken) {
		// jsonをパースするときの設定。
		ClientConfig config = new ClientConfig().register(JacksonFeature.class);
		// 設定情報を用いてclientを構築し、さらにターゲットを構築する。
		WebTarget target = ClientBuilder.newClient(config).target("https://api.github.com")
				.queryParam("access_token", accessToken)
				.path("/user");
		// ターゲットに対してリクエストを送信し、GithubUserクラスで返してもらう。
		final GithubUser user = target.request(MediaType.APPLICATION_JSON).get(GithubUser.class);
		System.out.println(user);
		return user;
	}

	/**
	 * AccessTokenを解析する。
	 *
	 * @param request Httpリクエスト
	 * @return AccessToken
	 */
	private static String parseAccessToken(HttpServletRequest request) {
		// URLのパラメータからcode、stateを取得する。
		final String code = request.getParameter("code");
		final String state = request.getParameter("state");
		// TokenResultを取得する。
		final TokenResult tokenResult = Shared.getGithubFlow().finish(code, state);
		// AccessTokenを取得する。
		final String accessToken = tokenResult.getAccessToken();
		System.out.printf("accessToken:%s\n", accessToken);
		return accessToken;
	}
}
