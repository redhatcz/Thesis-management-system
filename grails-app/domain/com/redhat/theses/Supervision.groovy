package com.redhat.theses

class Supervision {

    Topic topic
    Membership membership

    static constraints = {
        membership unique: ['topic']
    }
}
