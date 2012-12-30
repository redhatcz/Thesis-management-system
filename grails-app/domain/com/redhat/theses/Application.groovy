package com.redhat.theses

import com.redhat.theses.auth.User

class Application {

    User applicant
    Topic topic
    University university
    User supervisor
    Date dateCreated
    String note;
    Boolean approvedByOwner = false;

    static constraints = {
        university validator: { university, application ->
            university.id in application.applicant.organizations*.id
        }

        supervisor validator: { supervisor, application ->
            supervisor.id in application.topic.supervisions
                    .collect { it.membership }
                    .findAll { it.organizationId == application.universityId }*.userId
        }
    }
}
