 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic {

    String title
    String lead
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

    static List<Supervision> findAllByTag(Tag tag){
        Topic.findAll('FROM Topic t where :tag member of t.tags', [tag: tag])
    }

    def beforeInsert(){
        filterTags()
    }

    def beforeUpdate(){
        filterTags()
    }

    List<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this)
    }

//    TODO: possible refactoring
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
