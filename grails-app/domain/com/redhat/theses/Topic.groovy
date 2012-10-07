package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    Date dateCreated
    User Supervisor

    static hasMany = [tags : Tag]
    static constraints = {
    }
}
