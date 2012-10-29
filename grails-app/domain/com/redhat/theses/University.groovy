package com.redhat.theses

import com.redhat.theses.auth.User
import groovy.transform.ToString
import org.springframework.dao.InvalidDataAccessApiUsageException

class University {
    String name;

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
    }

    Set<User> getUsers() {
        try {
            Membership.findAllByUniversity(this).collect {it.user} as Set
        } catch (InvalidDataAccessApiUsageException e) {
            //this object has not been saved yet, so return empty collection
            new HashSet<User>()
        }
    }

    boolean  hasMember(User user){
        Membership.countByUserAndUniversity(user, this) > 0
    }

    String toString(){
        name
    }
}
