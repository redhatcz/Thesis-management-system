package com.redhat.theses.auth

import grails.validation.Validateable

/**
 * @author vdedik@redhat.com
 */
@Validateable
class RegistrationCommand {

    String username
    String password
    String repeatPassword
    String fullName

    static constraints = {
        username blank: false, minSize: 4
        password blank: false, minSize: 6
        fullName blank: false
        repeatPassword blank: false, validator: { val, obj ->
            val == obj.password
        }
    }
}
