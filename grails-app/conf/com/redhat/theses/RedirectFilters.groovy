package com.redhat.theses

class RedirectFilters {

    def grailsApplication

    def filters = {

        /*
         * Redirects all requests to domain other than grails.serverUrl to grails.serverURL
         */
        all(controller:'*', action:'*') {
            before = {
                try{
                    def serverURL = grailsApplication.config.grails.serverURL
                    URI serverURI = new URI(serverURL)
                    def domain = serverURI.host

                    if (domain && request.serverName != domain) {
                        response.setStatus(301);
                        response.setHeader("Location", serverURL + request.forwardURI)
                        response.flushBuffer()
                        return false // return false, otherwise request is handled from controller
                    }
                } catch (Exception e) {
                    // Catch all exceptions, otherwise we could get into an endless loop
                    log.error "Permanent redirection failed, exception message: " + e.getMessage()
                    log.error e
                }
            }
        }
    }
}