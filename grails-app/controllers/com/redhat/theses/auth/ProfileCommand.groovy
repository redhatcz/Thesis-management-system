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
    boolean sendMail

    static constraints = {
        password minSize: 6
        email blank: false, email: true
        fullName blank: false
        repeatPassword validator: { val, obj ->
            if (val != obj.password) {
                'not.match'
            }
        }
        repeatEmail validator: {val, obj ->
            def user = User.findByEmail(obj.email)
            if (!user && val != obj.email) {
                'not.match'
            }
        }
        oldPassword blank: false, validator: { val, obj ->
            if (User.findByPassword(obj.springSecurityService.encodePassword(val)) == null) {
                'wrong'
            }
        }
        email validator: {val, obj ->
            if (User.findByEmail(val) != obj.springSecurityService.currentUser && User.findByEmail(val)) {
                'not.unique'
            }
        }
    }
}
