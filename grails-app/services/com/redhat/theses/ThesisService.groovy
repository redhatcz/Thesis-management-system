package com.redhat.theses

import com.redhat.theses.events.ArticleEvent
import com.redhat.theses.util.Commons

class ThesisService {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    Thesis save(Thesis thesis, fireEvent = true) {
        String type = thesis.id ? 'articleUpdated' : 'articleCreated'
        def persistedThesis = thesis.save()

        if (persistedThesis && fireEvent) {
            event(type, new ArticleEvent(persistedThesis, springSecurityService.currentUser,
                    [thesis.supervisor, thesis.assignee, thesis.topic.owner]))
        }
        persistedThesis
    }

    Boolean delete(Thesis thesis) {
        def subscriptions = Subscription.findAllByArticle(thesis)
        def success = Comment.findAllByArticle(thesis).every { Commons.delete(it) } &&
                subscriptions.every { Commons.delete(it) } &&
                Commons.delete(thesis)

        if (success) {
            event('articleDeleted', new ArticleEvent(thesis, springSecurityService.currentUser, subscriptions*.subscriber))
        }
        success
    }
}
