package com.redhat.theses.auth

import grails.validation.Validateable
import com.redhat.theses.University

/**
 * @author vdedik@redhat.com
 */
@Validateable
class RegistrationCommand {

    String username
    String password
    String repeatPassword
    String email
    String repeatEmail
    String fullName
    University university

    static constraints = {
        username blank: false, minSize: 4
        password blank: false, minSize: 6
        email blank: false, email: true
        fullName blank: false
        repeatPassword validator: { val, obj ->
            val == obj.password
        }
        repeatEmail validator: {val, obj ->
            val == obj.email
        }
        university nullable: false
    }
}
