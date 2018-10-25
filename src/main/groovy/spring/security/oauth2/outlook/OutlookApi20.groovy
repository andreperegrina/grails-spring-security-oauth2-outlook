package spring.security.oauth2.outlook

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.extractors.TokenExtractor
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.OAuthConfig
import com.github.scribejava.core.model.OAuthConstants
import com.github.scribejava.core.model.ParameterList
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth20Service

class OutlookApi20 extends DefaultApi20 {


    protected OutlookApi20() {
    }

    private static class InstanceHolder {
        private static final OutlookApi20 INSTANCE = new OutlookApi20()
    }

    static OutlookApi20 instance() {
        return InstanceHolder.INSTANCE
    }


    @Override
    Verb getAccessTokenVerb() {
        return Verb.POST
    }


    @Override
    String getAccessTokenEndpoint() {
        return "https://login.microsoftonline.com/%s/oauth2/v2.0/token"
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://login.microsoftonline.com/common/oauth2/v2.0/authorize"
    }

    @Override
    TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return super.getAccessTokenExtractor()
    }

    @Override
    String getAuthorizationUrl(OAuthConfig config, Map<String, String> additionalParams) {
        final ParameterList parameters = new ParameterList(additionalParams)
        parameters.add(OAuthConstants.RESPONSE_TYPE, "code id_token")
        parameters.add(OAuthConstants.CLIENT_ID, config.getApiKey())


        UUID expectedState = UUID.randomUUID()
        UUID expectedNonce = UUID.randomUUID()
        parameters.add("state", expectedState.toString())
        parameters.add("nonce", expectedNonce.toString())

        parameters.add("response_mode", "form_post")

        final String callback = config.getCallback()
        if (callback != null) {
            parameters.add(OAuthConstants.REDIRECT_URI, callback)
        }

        final String scope = config.getScope()
        if (scope != null) {
            parameters.add(OAuthConstants.SCOPE, scope)
        }

        return parameters.appendTo(getAuthorizationBaseUrl())
    }

    @Override
    OAuth20Service createService(OAuthConfig config) {
        return new OutlookOAuth20Service(this, config)
    }
}
