package com.redhat.theses

import com.redhat.theses.auth.User

class Supervision implements Serializable {

    Topic topic
    User supervisor
    University university

    static constraints = {
        supervisor unique: ['university', 'topic']
    }

    static mapping = {
        version false
    }
}
