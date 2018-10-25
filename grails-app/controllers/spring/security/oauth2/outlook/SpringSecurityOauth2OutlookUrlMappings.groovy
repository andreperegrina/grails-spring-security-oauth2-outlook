package spring.security.oauth2.outlook

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.util.Holders

class SpringSecurityOauth2OutlookUrlMappings {

    static mappings = {
        def active = Holders.grailsApplication.config.grails?.plugin?.springsecurity?.oauth2?.active
        def enabled = (active instanceof Boolean) ? active : true
        if (enabled && SpringSecurityUtils.securityConfig?.active) {
            "/oauth2/outlook/callback"(controller: 'springSecurityOAuth2Outlook', action: 'callback')
        }
    }
}
