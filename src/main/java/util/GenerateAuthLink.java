package util;

import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;


public class GenerateAuthLink {
	public static void main(String[] args) throws OAuthSystemException  {
//		AuthenticationRequestBuilder builder = new OAuthClientRequest.AuthenticationRequestBuilder("https://www.facebook.com/dialog/oauth");
//		builder.setClientId("573292326025854");
//		builder.setRedirectURI("https://apps.facebook.com/rhythmtest/CallbackServl");
//		OAuthClientRequest request = builder.buildQueryMessage();
		
		System.out.println(generateTokenUrl());
	}
	
	public static String generate() throws OAuthSystemException{
		OAuthClientRequest request = OAuthClientRequest
				.authorizationLocation("https://www.facebook.com/dialog/oauth")
				.setClientId("573292326025854")
				.setRedirectURI("https://apps.facebook.com/rhythmtest/CallbackServl")
				.setScope("email,publish_actions")
				.buildQueryMessage();
		
		return request.getLocationUri();
	}
	
	public static String generateTokenUrl() throws OAuthSystemException{
		OAuthClientRequest request = OAuthClientRequest
				.authorizationLocation("https://www.facebook.com/dialog/oauth")
				.setClientId("573292326025854")
				.setResponseType("token")
				.setRedirectURI("https://apps.facebook.com/rhythmtest/call_back.jsp")
				.setScope("email,publish_actions")
				.buildQueryMessage();
		
		return request.getLocationUri();
	}
}
