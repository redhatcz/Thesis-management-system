package com.redhat.theses.auth

import grails.validation.Validateable

/**
 * @author vdedik@redhat.com
 */
@Validateable
class PasswordCommand {

    def springSecurityService

    String oldPassword
    String password
    String repeatPassword

    static constraints = {
        oldPassword blank: false, validator: { val, obj ->
            obj.springSecurityService.encodePassword(val) == obj.springSecurityService.currentUser.password
        }
        password blank: false, minSize: 6
        repeatPassword validator: { val, obj ->
            val == obj.password
        }
    }
}
