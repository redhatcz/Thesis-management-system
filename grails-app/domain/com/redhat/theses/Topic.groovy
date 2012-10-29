 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String description
    Date dateCreated
    User owner

    static hasMany = [tags : Tag]
    static constraints = {
        description widget: 'textarea' , nullable: false, blank: false
        title nullable: false, blank: false
        owner nullable: false
    }

    Set<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this) as Set
    }

    static mapping = {
        description type: 'text'
    }

    String toString(){
        title
    }
}
