package com.redhat.theses

import com.redhat.theses.auth.User

class Thesis extends Article{

    String title
    String description

    Topic topic
    User assignee
    User supervisor
    Status status
    Grade grade
    String thesisAbstract
    Date dateCreated

    static mapping = {
        description type: 'text'
        thesisAbstract type: 'text'
        sort 'dateCreated'
        order 'desc'
    }

    static constraints = {
        title blank: false
        description blank: false
        grade nullable: true
        supervisor nullable: true
        thesisAbstract nullable: true

        topic validator: {topic ->
            topic?.id != null && Topic.get(topic.id)
        }

        assignee validator: {assignee ->
            assignee?.id != null && User.get(assignee.id)
        }
    }

    static bucketMapping = "thesis"

    public List<String> getFileURLs(){
       return null;
    }

}
