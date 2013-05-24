 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic extends Article {

    String secondaryTitle

    String lead
    String description
    String secondaryDescription
    Date dateCreated
    User owner
    Boolean enabled = true

    //static searchable = {
    //    root true
    //    owner component: true
    //}

    static hasMany = [universities: University, categories: Category, tags: Tag, types: Type]

    static constraints = {
        description widget: 'textarea', blank: false
        lead blank: false
        secondaryTitle nullable: true
        secondaryDescription nullable: true

        owner validator: {owner ->
            if (owner?.id == null || !User.get(owner.id)) {
                'not.found'
            }
        }
    }

    static mapping = {
        description type: 'text'
        lead type: 'text'
        secondaryDescription type: 'text'
        sort 'dateCreated'
        order 'desc'
    }

    static List<Topic> findAllByUniversity(University university, Map params = [:]) {
        Topic.findAll(
                'from Topic t where :university member of t.universities order by t.dateCreated desc',
                [university: university], params)
    }

    static List<Topic> findAllBySupervisor(User supervisor, Map params = [:]){
        Topic.executeQuery('SELECT s.topic FROM Supervision s WHERE s.supervisor=:supervisor',
                [supervisor: supervisor], params).unique()
    }

    List<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this)
    }

    List<User> getSupervisors() {
        getSupervisions()*.supervisor.unique()
    }

    String toString(){
        title
    }
}
