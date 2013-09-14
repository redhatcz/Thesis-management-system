package com.redhat.theses

import com.redhat.theses.events.ArticleEvent
import com.redhat.theses.util.Commons

class ThesisService {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Saves a thesis
     *
     * @param thesis - thesis to be saved
     * @param fireEvent - if to fire event on success
     * @return true if success, false otherwise
     */
    Thesis  save(Thesis thesis, fireEvent = true) {
        String type = thesis.id ? 'articleUpdated' : 'articleCreated'
        def persistedThesis = thesis.save()

        if (persistedThesis && fireEvent) {
            event(type, new ArticleEvent(persistedThesis, springSecurityService.currentUser,
                    [thesis.supervisor, thesis.assignee, thesis.topic.owner]))
        }
        persistedThesis
    }

    /**
     * Deletes thesis safely
     *
     * @return true if success, false otherwise
     */
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
