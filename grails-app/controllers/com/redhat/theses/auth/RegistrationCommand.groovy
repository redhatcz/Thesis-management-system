package com.redhat.theses.auth
import grails.validation.Validateable
/**
 * @author vdedik@redhat.com
 */
@Validateable
class RegistrationCommand {

    String email
    String repeatEmail
    String password
    String repeatPassword
    String fullName

    static constraints = {
        password blank: false, minSize: 6
        email blank: false, email: true
        fullName blank: false
        repeatPassword validator: { val, obj ->
            val == obj.password
        }
        repeatEmail validator: {val, obj ->
            val == obj.email
        }
    }
}
