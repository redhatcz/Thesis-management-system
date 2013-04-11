package com.redhat.theses

/**
 * Adds security headers to http response
 */
class SecurityFilters {

    def filters = {
        all(controller:'*', action:'*') {
            after = { Map model ->
                response.addHeader("X-Frame-Options", "DENY")
            }
        }
    }
}
