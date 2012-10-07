package com.redhat.theses

class Tag {

//    static hasMany = [subTags: Tag]
    static belongsTo = [parent: Tag]

    String title

    static constraints = {
        title unique: true
        parent blank: true, nullable: true

    }
}
