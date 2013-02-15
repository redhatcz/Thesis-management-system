package com.redhat.theses.auth

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority

class CustomGrailsUser extends GrailsUser {
    String fullName

    CustomGrailsUser(String email, String fullName, String password, 
        Boolean enabled, Boolean accountNotExpired, Boolean passwordNotExpired,
        Boolean accountNotLocked, Collection<GrantedAuthority> authorities, Long id) {

        super(email, password, enabled, accountNotExpired, passwordNotExpired, 
            accountNotLocked, authorities, id)

        this.fullName = fullName
    }
}
