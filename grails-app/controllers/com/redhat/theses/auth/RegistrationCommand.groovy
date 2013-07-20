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
    Boolean termsOfUse = false

    static constraints = {
        password blank: false, minSize: 6
        email blank: false, email: true
        fullName blank: false
        termsOfUse validator: {val ->
            if (val != true){
                'disagree'
            }
        }
        repeatPassword validator: { val, obj ->
            if (val != obj.password) {
                'not.match'
            }
        }
        repeatEmail validator: {val, obj ->
            if (val != obj.email) {
                'not.match'
            }
        }
        email validator: {val, obj ->
            User.withoutHibernateFilters{
                if (User.findByEmail(val)) {
                    'not.unique'
                }
            }
        }
    }
}
