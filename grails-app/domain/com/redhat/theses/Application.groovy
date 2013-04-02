package com.redhat.theses

import com.redhat.theses.auth.User

class Application {

    User applicant
    Topic topic
    Thesis thesis
    University university
    Date dateCreated
    String note
    Boolean approved = false

    static constraints = {
        thesis nullable: true

        university validator: { university, application ->
            university.id in application.topic.universities*.id
        }
    }
}
