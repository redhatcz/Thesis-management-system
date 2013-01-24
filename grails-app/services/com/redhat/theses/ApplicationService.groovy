package com.redhat.theses

class ApplicationService {

    def Application approve(Application application) {
        if (application.approvedByOwner) {
            return
        }

        application.approvedByOwner = true
        def result = application.save()
        if (result) {
            event('applicationApproved', application)
        }
        result
    }

}
