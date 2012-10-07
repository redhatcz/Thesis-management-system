package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String primaryAnnotation
    String secondaryAnnotation
    Date dateCreated
    User Supervisor

    static hasMany = [tags : Tag]
    static constraints = {
        secondaryAnnotation nullable: true
    }
}
