package com.redhat.theses

import com.redhat.theses.auth.User

class Thesis extends Article{

    Topic topic
    User assignee
    Status status
    Grade grade
    String thesisAbstract


    static constraints = {
        grade nullable: true
        thesisAbstract nullable: true
    }

    static enum Status {
        IN_PROGRESS,
        FINISHED,
        FAILED,
        POSPONED
    }
    static enum Grade {
        A, B, C, D, E, F
    }

    public List<String> getFileURLs(){
       return null;
    }
}
