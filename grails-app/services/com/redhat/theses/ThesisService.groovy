package com.redhat.theses

class ThesisService {

    def Thesis createFromApplication(Application application) {
        Thesis thesis = null
        if (!Thesis.findByApplication(application)){
            thesis = new Thesis(application: application, status: Thesis.Status.IN_PROGRESS, topic: application.topic).save()
        }
        thesis
    }
}
