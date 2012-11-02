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
        try {
            Membership.findAllByOrganization(this).collect {it.user} as Set
        } catch (InvalidDataAccessApiUsageException e) {
            //this object has not been saved yet, so return empty collection
            new HashSet<User>()
        }
    }

    boolean  hasMember(User user){
        Membership.countByUserAndOrganization(user, this) > 0
    }

    String toString(){
        name
    }
}
