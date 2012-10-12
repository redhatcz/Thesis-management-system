package com.redhat.theses

import groovy.transform.ToString

@ToString(includes='title')
class Tag {

    static hasMany = [subTags: Tag]
    static belongsTo = [parent: Tag]

    String title

    static constraints = {
        title   unique: true
        parent  nullable: true
    }

    String toString(){
        title
    }
}