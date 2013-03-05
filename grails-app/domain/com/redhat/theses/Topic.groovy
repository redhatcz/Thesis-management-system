 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic extends Article {

    String title
    String lead
    String description
    Date dateCreated
    User owner
    Company company

    static hasMany = [universities : University, tags : Tag]

    static constraints = {
        description widget: 'textarea' , nullable: false, blank: false
        title nullable: false, blank: false
        lead nullable: false, blank: false
    }

    static mapping = {
        description type: 'text'
        lead type: 'text'
        sort 'dateCreated'
        order 'desc'
    }

    static List<Topic> findAllByTag(Tag tag, Map params = [:]){
        Topic.findAll('FROM Topic t where :tag member of t.tags', [tag: tag], params)
    }

    static List<Topic> findAllBySupervisor(User supervisor, Map params = [:]){
        Topic.executeQuery('SELECT s.topic FROM Supervision s WHERE s.supervisor=:supervisor', [supervisor: supervisor], params).unique()
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

    List<User> getSupervisors() {
        Supervision.findAllByTopic(this)*.supervisor.unique()
    }

//    TODO: possible refactoring
    private filterTags(){
        if (!tags){
            return
        }
        def filtered = new ArrayList<Tag>(tags)
        tags.each {
            if (it) {
                filtered.removeAll(it.allParents)
            }
        }
        tags = filtered
    }

    String toString(){
        title
    }
}
