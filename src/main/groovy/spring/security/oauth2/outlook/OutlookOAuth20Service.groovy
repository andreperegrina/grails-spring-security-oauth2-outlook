package spring.security.oauth2.outlook

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.model.AbstractRequest
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.OAuthConfig
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth20Service
import com.outlook.dev.auth.IdToken

class OutlookOAuth20Service extends OAuth20Service {

    /**
     * Default constructor
     *
     * @param api OAuth2.0 api information
     * @param config OAuth 2.0 configuration param object
     */
    OutlookOAuth20Service(DefaultApi20 api, OAuthConfig config) {
        super(api, config)
    }

    @Override
    protected <T extends AbstractRequest> T createAccessTokenRequest(String code, T request) {
        def splitCode = code.split("\\|")
        IdToken idTokenObj = IdToken.parseEncodedToken(splitCode[0], "")
        def requestModify = new OAuthRequest(Verb.POST, String.format(request.url, idTokenObj.tenantId), request.service)
        super.createAccessTokenRequest(splitCode[1], requestModify) as T
    }

    @Override
    void signRequest(OAuth2AccessToken accessToken, AbstractRequest request) {
        request.addHeader("Authorization", "$accessToken.tokenType $accessToken.accessToken")
    }
}
