package com.redhat.theses

class Supervision implements Serializable {

    Topic topic
    Membership membership

    static constraints = {

    }

    static mapping = {
        id composite: ['topic', 'membership']
        version false
    }
}
