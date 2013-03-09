 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic extends Article {

    String title
    String lead
    String description
    Date dateCreated
    User owner
    Boolean enabled = true

    static hasMany = [universities : University, categories : Category, types : Type]

    static hibernateFilters = {
        enabledFilter(condition:"enabled='1'", default: true)
    }

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

    static List<Topic> findAllByCategory(Category category, Map params = [:]){
        Topic.findAll('from Topic t where :category member of t.categories', [category: category])
    }

    static Long countByCategory(Category category){
        Topic.executeQuery('select count(distinct t) from Topic t where :category member of t.categories', [category: category])[0]
    }

    static List<Topic> findAllBySupervisor(User supervisor, Map params = [:]){
        Topic.executeQuery('SELECT s.topic FROM Supervision s WHERE s.supervisor=:supervisor', [supervisor: supervisor], params).unique()
    }

    List<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this)
    }

    List<User> getSupervisors() {
        Supervision.findAllByTopic(this)*.supervisor.unique()
    }

    String toString(){
        title
    }
}
