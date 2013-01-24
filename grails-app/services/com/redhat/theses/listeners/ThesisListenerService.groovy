package com.redhat.theses.listeners

import com.redhat.theses.Subscription
import com.redhat.theses.events.ThesisEvent
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author vdedik@redhat.com
 */
class ThesisListenerService {
    /*
     * Dependency injection of FeedService
     */
    def feedService

    /*
     * Dependency injection of MessageSource
     */
    def messageSource

    @Listener(topic = "thesisCreated")
    void thesisCreated(ThesisEvent e) {
        feedService.createThesisFeed(e.thesis, 'insert', e.user)
    }

    @Listener(topic = "thesisDeleted")
    void thesisDeleted(ThesisEvent e) {
        feedService.createThesisFeed(e.thesis, 'delete', e.user)
    }

    @Listener(topic = "thesisUpdated")
    void thesisUpdated(ThesisEvent e) {
        def feed = feedService.createThesisFeed(e.thesis, 'update', e.user)

        def subscribers = Subscription.findAllByArticle(e.thesis)*.subscriber.unique()
        //remove currentUser from subscribers
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        filteredSubscribers.each {
            //TODO: send email instead of printing the message to stdout, and also refactoring needed
            println "Sending mail to subscriber ${it.fullName}:"
            println messageSource.getMessage(feed.messageCode, feed.args.toArray(), LocaleContextHolder.getLocale())
        }
    }
}
