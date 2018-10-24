package com.outlook.dev.auth;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

public class TokenResponse {
	@SerializedName("token_type")
	private String tokenType;
	private String scope;
	@SerializedName("expires_in")
	private int expiresIn;
	@SerializedName("access_token")
	private String accessToken;
	@SerializedName("refresh_token")
	private String refreshToken;
	@SerializedName("id_token")
	private String idToken;
	private String error;
	@SerializedName("error_description")
	private String errorDescription;
	@SerializedName("error_codes")
	private int[] errorCodes;
	private Date expirationTime;

	public TokenResponse() {
	}

	public TokenResponse(int expiresIn, String accessToken, String refreshToken) {
		this.expiresIn = expiresIn;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, expiresIn);
		this.expirationTime = now.getTime();
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public int[] getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(int[] errorCodes) {
		this.errorCodes = errorCodes;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
}
