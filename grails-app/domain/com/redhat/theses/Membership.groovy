package com.redhat.theses

import com.redhat.theses.auth.User

class Membership implements Serializable    {

    User user;
    Organization organization

    static mapping = {
        version false
    }

    static constraints = {
        user unique: ['organization']
    }
}
