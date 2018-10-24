package com.outlook.dev.auth;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

public class AuthHelper {
	public static final String authority = "https://login.microsoftonline.com";
	public static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";
	
	private static String[] scopes = {
		"openid", 
		"offline_access",
		"profile", 
		"User.Read",
		"Mail.Read",
		"Calendars.Read",
		"Contacts.Read"
	};
	
	private static String appId = "c8054a45-32a9-42d4-8229-eb18df190a45";
	private static String appPassword = "QLAB6?juvkkcpXZD4563;]=";
	private static String redirectUrl = "https://localhost:8443/kairos/integration/callbackOAuthOutlook";
	
	private static String getAppId() {
		if (appId == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appId;
	}
	private static String getAppPassword() {
		if (appPassword == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appPassword;
	}
	
	private static String getRedirectUrl() {
		if (redirectUrl == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return redirectUrl;
	}
	
	private static String getScopes() {
		StringBuilder sb = new StringBuilder();
		for (String scope: scopes) {
			sb.append(scope + " ");
		}
		return sb.toString().trim();
	}
	
	private static void loadConfig() throws IOException {
		String authConfigFile = "auth.properties";
		InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);
		
		if (authConfigStream != null) {
			Properties authProps = new Properties();
			try {
				authProps.load(authConfigStream);
				appId = authProps.getProperty("appId");
				appPassword = authProps.getProperty("appPassword");
				redirectUrl = authProps.getProperty("redirectUrl");
			} finally {
				authConfigStream.close();
			}
		}
		else {
			throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
		}
	}
	
	public static String getLoginUrl(UUID state, UUID nonce) {
		
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
		urlBuilder.queryParam("client_id", getAppId());
		urlBuilder.queryParam("redirect_uri", getRedirectUrl());
		urlBuilder.queryParam("response_type", "code id_token");
		urlBuilder.queryParam("scope", getScopes());
		urlBuilder.queryParam("state", state);
		urlBuilder.queryParam("nonce", nonce);
		urlBuilder.queryParam("response_mode", "form_post");
		
		return urlBuilder.toUriString();
	}

	public static String getLoginUrlSpringSecurity(UUID state, UUID nonce) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
		urlBuilder.queryParam("state", state);
		urlBuilder.queryParam("nonce", nonce);
		urlBuilder.queryParam("response_mode", "form_post");
		String url=urlBuilder.toUriString();
		return url+"&response_type=code id_token&client_id=%s&redirect_uri=%s";
	}


}
