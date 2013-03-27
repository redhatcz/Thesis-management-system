package com.redhat.theses

class SecurityFilters {

    def filters = {
        all(controller:'*', action:'*') {
            after = { Map model ->
                response.addHeader("X-Frame-Options", "DENY")
                response.addHeader("X-Content-Security-Policy", "allow 'self';")
                response.addHeader("X-XSS-Protection", "1; mode=block")
            }
        }
    }
}
