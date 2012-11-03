 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String description
    Date dateCreated
    User owner
    Company company

    static hasMany = [tags : Tag]
    static constraints = {
        description widget: 'textarea' , nullable: false, blank: false
        title nullable: false, blank: false
        owner nullable: false
    }

    def beforeInsert(){
        filterTags()
    }

    def beforeUpdate(){
        filterTags()
    }

    Set<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this) as Set
    }

    private filterTags(){
        def filtered = new ArrayList<Tag>(tags)
        tags.each {
            filtered.removeAll(it.allParents)
        }
        tags = filtered
    }

    static mapping = {
        description type: 'text'
    }

    String toString(){
        title
    }
}
