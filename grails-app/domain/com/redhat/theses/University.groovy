package com.redhat.theses

import com.redhat.theses.auth.User
import groovy.transform.ToString

class University {
    String name;

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
    }

    Set<User> getUsers(){
        Membership.findAllByUniversity(this).collect {it.user} as Set
    }

    boolean  hasMember(User user){
        Membership.countByUserAndUniversity(user, this) > 0
    }

    String toString(){
        name
    }
}
