package com.jboss.theses

import com.jboss.theses.auth.User

class Topic {

    String title
    Date dateCreated
    User Supervisor

    static hasMany = [tags : Tag]
    static constraints = {
    }
}
