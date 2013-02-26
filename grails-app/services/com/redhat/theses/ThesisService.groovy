package com.redhat.theses

import com.redhat.theses.events.ThesisEvent
import com.redhat.theses.util.Commons

class ThesisService {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    Thesis createFromApplication(Application application) {
        Thesis persistedThesis =  new Thesis(status: Status.IN_PROGRESS, topic: application.topic,
                assignee: application.applicant, supervisor: application.supervisor).save()

        if (persistedThesis) {
            event('thesisCreated', new ThesisEvent(persistedThesis, springSecurityService.currentUser))
        }

        persistedThesis
    }

    Thesis save(Thesis thesis) {
        String type = thesis.id ? 'thesisUpdated' : 'thesisCreated'
        def persistedThesis = thesis.save()

        if (persistedThesis) {
            event(type, new ThesisEvent(persistedThesis, springSecurityService.currentUser))
        }
        persistedThesis
    }

    Boolean delete(Thesis thesis) {
        def success = Commons.delete(thesis)

        if (success) {
            event('thesisDeleted', new ThesisEvent(thesis, springSecurityService.currentUser))
        }
        success
    }
}
