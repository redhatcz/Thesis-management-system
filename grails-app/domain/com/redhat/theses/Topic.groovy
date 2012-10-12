 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String description
    Date dateCreated
    User owner

    static hasMany = [tags : Tag]
    static constraints = {
        description widget: 'textarea'
    }

    Set<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this).collect {it.membership} as Set
    }

    static mapping = {
        description type: 'text'
        secondaryAnnotation type: 'text'
    }

    String toString(){
        title
    }
}
