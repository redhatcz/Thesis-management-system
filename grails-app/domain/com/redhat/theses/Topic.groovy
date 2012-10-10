 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String primaryAnnotation
    String secondaryAnnotation
    Date dateCreated
    User supervisor

    static hasMany = [tags : Tag]
    static constraints = {
        primaryAnnotation widget: 'textarea'
        secondaryAnnotation nullable: true, widget: 'textarea'
    }
    static mapping = {
        primaryAnnotation type: 'text'
        secondaryAnnotation type: 'text'
    }
}
