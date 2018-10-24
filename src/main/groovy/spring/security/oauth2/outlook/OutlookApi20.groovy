package spring.security.oauth2.outlook

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.extractors.TokenExtractor
import com.github.scribejava.core.model.OAuth2AccessToken
import com.outlook.dev.auth.AuthHelper

class OutlookApi20 extends DefaultApi20  {

    private static final UUID expectedState=UUID.randomUUID()
    private static final UUID expectedNonce=UUID.randomUUID()

    protected OutlookApi20() {
    }

    private static class InstanceHolder {
        private static final OutlookApi20 INSTANCE = new OutlookApi20()
    }

    static OutlookApi20 instance() {
        return InstanceHolder.INSTANCE
    }

    @Override
    String getAccessTokenEndpoint() {
        return AuthHelper.authority + "/%s/oauth2/v2.0/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return AuthHelper.getLoginUrlSpringSecurity(expectedState, expectedNonce)
    }

    @Override
    TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return super.getAccessTokenExtractor()
    }
}
