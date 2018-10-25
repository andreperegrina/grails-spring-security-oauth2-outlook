package com.outlook.dev.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.codec.binary.Base64;
import org.grails.web.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Date;

public class IdToken {
    // NOTE: This is just a subset of the claims returned in the
    // ID token. For a full listing, see:
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens
    @SerializedName("exp")
    private long expirationTime;
    @SerializedName("nbf")
    private long notBefore;
    @SerializedName("tid")
    private String tenantId;
    private String nonce;
    private String name;
    private String email;
    @SerializedName("preferred_username")
    private String preferredUsername;
    @SerializedName("oid")
    private String objectId;

    public static IdToken parseEncodedToken(String encodedToken, String nonce) {
        // Encoded token is in three parts, separated by '.'
        String[] tokenParts = encodedToken.split("\\.");

        // The three parts are: header.token.signature
        String idToken = tokenParts[1];
        byte[] decodedBytes = Base64.decodeBase64(idToken);
        String tokenString = new JSONObject(new String(decodedBytes)).toString();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        IdToken newToken = null;
        try {
            newToken = gson.fromJson(tokenString, IdToken.class);
//            if (!newToken.isValid(nonce)) {
//                return null;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newToken;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(long notBefore) {
        this.notBefore = notBefore;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    private Date getUnixEpochAsDate(long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do
        // the conversion.
        return new Date(epoch * 1000);
    }

    private boolean isValid(String nonce) {
        // This method does some basic validation
        // For more information on validation of ID tokens, see
        // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
        Date now = new Date();

        // Check expiration and not before times
        if (now.after(this.getUnixEpochAsDate(this.expirationTime)) ||
                now.before(this.getUnixEpochAsDate(this.notBefore))) {
            // Token is not within it's valid "time"
            return false;
        }

        // Check nonce
        if (!nonce.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }
}
