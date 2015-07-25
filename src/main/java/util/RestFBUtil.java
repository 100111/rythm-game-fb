package util;

import javax.servlet.http.HttpSession;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

public class RestFBUtil {
	public static void initRestFB(String token, HttpSession session) {
		FacebookClient client = null;
		
		if(session.getAttribute("restFBClient") != null) {
			client = getRestFBClient(session);
		} else {
			client = new DefaultFacebookClient(token);
			session.setAttribute("restFBClient", client);
			session.setAttribute("token",token);
		}

	}

	public static FacebookClient getRestFBClient(HttpSession session) {
		return (FacebookClient) session.getAttribute("restFBClient");
	}
}
