![header](./header.png)
Spring Security OAuth2 Outlook Plugin
====================================
[ ![Download](https://api.bintray.com/packages/andreperegrina/plugins/spring-security-oauth2-outlook/images/download.svg) ](https://bintray.com/andreperegrina/plugins/spring-security-oauth2-outlook/_latestVersion)

Add a Outlook OAuth2 provider to the [Spring Security OAuth2 Plugin](https://github.com/MatrixCrawler/grails-spring-security-oauth2).

Installation
------------
Add the following dependencies in `build.gradle`
```
dependencies {
...
    compile 'org.grails.plugins:spring-security-oauth2:1.+'
    compile 'org.grails.plugins:spring-security-oauth2-outlook:0.1'
...
}
```

Usage
-----
Add this to your application.yml
```
grails:
    plugin:
        springsecurity:
            oauth2:
                providers:
                    outlook:
                        api_key: 'outlook-api-key'               #needed
                        api_secret: 'outlook-api-secret'         #needed
                        successUri: "/oauth2/outlook/success"    #optional
                        failureUri: "/oauth2/outlook/failure"    #optional
                        callback: "/oauth2/outlook/callback"     #optional
                        scopes: "some_scope"                     #optional
```
You can replace the URIs with your own controller implementation.

In your view you can use the taglib exposed from this plugin and from OAuth plugin to create links and to know if the user is authenticated with a given provider:
```xml
<oauth2:connect provider="outlook" id="outlook-connect-link">Outlook</oauth2:connect>

Logged with outlook?
<oauth2:ifLoggedInWith provider="outlook">yes</oauth2:ifLoggedInWith>
<oauth2:ifNotLoggedInWith provider="outlook">no</oauth2:ifNotLoggedInWith>
```
License
-------
Apache 2
