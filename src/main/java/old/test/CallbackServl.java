package old.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.GitHubTokenResponse;
import org.apache.amber.oauth2.client.response.OAuthAuthzResponse;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;

import util.TempUtil;

/**
 * Servlet implementation class CallbackServl
 */
@WebServlet("/CallbackServl")
public class CallbackServl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CallbackServl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//oldProcess(request, response);
		PrintWriter out = response.getWriter();
		String accessToken = request.getParameter("access_token");
		out.print("Token: "+accessToken);
		out.print("Time: "+request.getParameter("expires_in"));
		
	}

	@SuppressWarnings("unused")
	private void oldProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
//		String code = request.getParameter("code");
		String token = request.getParameter("#access_token");
		
		
		if(token!= null) {
			out.print(token);
		} else {
		
		try {
//			TokenRequestBuilder builder = new TokenRequestBuilder("https://graph.facebook.com/oauth/access_token");
//			builder.setClientId("573292326025854");
//			builder.setClientSecret("356771b02f04425e4135efd0b1ade29f");
//			builder.setCode(code);
//			builder.setRedirectURI("http://192.168.194.56:8087/Rhythm_TestOltu/Callback");
//			builder.setGrantType(GrantType.AUTHORIZATION_CODE);
			
			/*
			OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
			
			OAuthClientRequest clientRequest = OAuthClientRequest
					.tokenLocation("https://graph.facebook.com/oauth/access_token")
					.setClientId("573292326025854")
					.setClientSecret("356771b02f04425e4135efd0b1ade29f")
					.setCode(oar.getCode())
					.setRedirectURI("https://apps.facebook.com/rhythmtest/CallbackServl")
					.buildBodyMessage();
			
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			
			GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(clientRequest, GitHubTokenResponse.class);
			
			String accessToken = oAuthResponse.getAccessToken();
            Long expiresIn = oAuthResponse.getExpiresIn();
            
            out.print("Token: " + accessToken);
            out.print("<br/>");
    		out.println("Time :"+expiresIn);
          	*/
			
			
    		OAuthAuthzResponse oar = OAuthAuthzResponse.oauthTokenAuthzResponse(request);
    		out.print("Token: " + oar.getAccessToken());
    		
			
		} catch (OAuthProblemException e) {
			out.print(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		
		}
	}

}
