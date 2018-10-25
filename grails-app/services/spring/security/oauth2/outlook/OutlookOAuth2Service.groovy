package spring.security.oauth2.outlook

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.model.OAuth2AccessToken
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.service.OAuth2AbstractProviderService
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken

@Transactional
class OutlookOAuth2Service extends OAuth2AbstractProviderService {

    @Override
    String getProviderID() {
        return 'outlook'
    }

    @Override
    Class<? extends DefaultApi20> getApiClass() {
        OutlookApi20.class
    }

    @Override
    String getProfileScope() {
        return "https://graph.microsoft.com/v1.0/me"
    }

    @Override
    String getScopes() {
        return 'openid profile User.Read Mail.Read User.ReadBasic.All'
    }

    @Override
    String getScopeSeparator() {
        return " "
    }

    @Override
    OAuth2SpringToken createSpringAuthToken(OAuth2AccessToken accessToken) {
        def user
        def response = getResponse(accessToken)
        try {
            log.debug("JSON response body: " + accessToken.rawResponse)
            user = JSON.parse(response.body)
        } catch (Exception exception) {
            log.error("Error parsing response from " + getProviderID() + ". Response:\n" + response.body)
            throw new OAuth2Exception("Error parsing response from " + getProviderID(), exception)
        }
        if (!user?.userPrincipalName) {
            log.error("No user email from " + getProviderID() + ". Response was:\n" + response.body)
            throw new OAuth2Exception("No user id from " + getProviderID())
        }
        new OutlookOauth2SpringToken(accessToken, user?.userPrincipalName, providerID)
    }

}
