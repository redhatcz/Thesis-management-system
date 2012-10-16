package com.redhat.theses

import com.redhat.theses.auth.User
import groovy.transform.ToString

@ToString(includes='name')
class University {
    String name;

    static constraints = {

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
