package com.redhat.theses.auth

import grails.validation.Validateable

/**
 * @author vdedik@redhat.com
 */
@Validateable
class EmailCommand {

    String email

    static constraints = {
        email blank: false, email: true, validator: { val ->
            if (!User.findByEmail(val)) {
                'not.exists'
            }
        }
    }
}
