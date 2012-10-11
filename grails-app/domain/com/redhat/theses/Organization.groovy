package com.redhat.theses

import com.redhat.theses.auth.User
import groovy.transform.ToString

@ToString(includes='name')
class Organization {
    String name;

    static hasMany = [members: User]
    static constraints = {

    }

    static mapping = {
        members fetch: 'join'
    }


}
