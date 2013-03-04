package com.redhat.theses.auth

import grails.validation.Validateable

/**
 * @author vdedik@redhat.com
 */
@Validateable
class ProfileCommand {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    String fullName
    String email
    String repeatEmail
    String password
    String repeatPassword
    String oldPassword

    static constraints = {
        password minSize: 6
        email blank: false, email: true
        fullName blank: false
        repeatPassword validator: { val, obj ->
            val == obj.password
        }
        repeatEmail validator: {val, obj ->
            def user = User.findByEmail(obj.email)
            user || val == obj.email
        }
        oldPassword blank: false, validator: { val, obj ->
            User.findByPassword(obj.springSecurityService.encodePassword(val)) != null
        }
    }
}
