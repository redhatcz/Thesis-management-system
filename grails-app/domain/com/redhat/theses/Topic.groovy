 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String primaryAnnotation
    String secondaryAnnotation
    Date dateCreated
    User owner

    static hasMany = [tags : Tag, supervison: Membership]
    static constraints = {
        primaryAnnotation widget: 'textarea'
        secondaryAnnotation nullable: true, widget: 'textarea'
    }

    static mapping = {
        primaryAnnotation type: 'text'
        secondaryAnnotation type: 'text'
    }

    Map<University, List<User>> getSupervision (){
        supervision.groupBy {it.university}.collectEntries {key, value ->
            [key, value.collect {it.user}]
        }
    }
}
