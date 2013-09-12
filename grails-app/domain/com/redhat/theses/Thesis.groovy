package com.redhat.theses

import com.redhat.theses.auth.User

class Thesis extends Article{

    String description

    Topic topic
    User assignee
    User supervisor
    Status status
    Grade grade
    String thesisAbstract
    String notes
    Date dateCreated
    University university
    Type type

    //static searchable = {
    //    root true
    //    assignee component: true
    //    supervisor component: true
    //}

    static hasMany = [tags: Tag]

    static mapping = {
        description type: 'text'
        thesisAbstract type: 'text'
        notes type: 'text'
        sort 'dateCreated'
        order 'desc'
    }

    static constraints = {
        description blank: false
        grade nullable: true
        supervisor nullable: true
        thesisAbstract nullable: true
        notes nullable: true

        topic validator: {topic ->
            if (topic?.id == null || !Topic.get(topic.id)) {
                'not.found'
            }
        }

        assignee validator: {assignee ->
            if (assignee?.id == null || !User.get(assignee.id)) {
                'not.found'
            }
        }

        supervisor validator: {supervisor ->
            if (supervisor?.id != null && !User.get(supervisor.id)) {
                'not.found'
            }
        }
    }

    static bucketMapping = "thesis"

    public List<String> getFileURLs(){
       return null;
    }

}
