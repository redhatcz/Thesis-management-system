package com.redhat.theses

import com.redhat.theses.auth.User
import org.springframework.dao.InvalidDataAccessApiUsageException

/**
 * @author vdedik@redhat.com
 */
class Organization {
    String name;

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
    }

    Set<User> getUsers() {
        if (Organization.find(this) == null) {
            return  [] as Set<User>
        }
        Membership.findAllByOrganization(this).collect {it.user} as Set
    }

    boolean  hasMember(User user){
        Membership.countByUserAndOrganization(user, this) > 0
    }

    String toString(){
        name
    }
}
