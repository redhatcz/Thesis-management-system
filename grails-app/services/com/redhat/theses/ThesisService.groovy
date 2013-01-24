package com.redhat.theses

class ThesisService {

    def Thesis createFromApplication(Application application) {
        Thesis thesis =  new Thesis(status: Thesis.Status.IN_PROGRESS,
                topic: application.topic, assignee: application.applicant).save()
        Application applicationWithThesis = Application.get(application.id)
        applicationWithThesis.thesis = thesis
        applicationWithThesis.save()
        thesis
    }
}
