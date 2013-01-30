package com.redhat.theses.listeners

import com.redhat.theses.Subscription
import com.redhat.theses.events.ThesisEvent
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

/**
 * @author vdedik@redhat.com
 */
class ThesisListenerService {

    /**
     * Dependency injection of com.redhat.theses.FeedService
     */
    def feedService

    /**
     * Dependency injection of com.redhat.theses.SubscriptionService
     */
    def subscriptionService

    /**
     * Dependency injection of org.springframework.context.MessageSource
     */
    def messageSource

    @Listener(topic = "thesisCreated")
    void thesisCreated(ThesisEvent e) {
        def feed = feedService.createThesisFeed(e.thesis, 'insert', e.user)

        def subscribers = [e.thesis.supervisor, e.thesis.assignee].unique()
        subscribers.each {
            subscriptionService.subscribe(it, e.thesis)
        }
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        def subject = messageSource.getMessage('mail.thesis.subscribed.subject', [e.thesis.id].toArray(), LCH.locale)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)
        subscriptionService.notifyAll(filteredSubscribers, subject, body)
    }

    //TODO: send all subscribers notification about deletion
    @Listener(topic = "thesisDeleted")
    void thesisDeleted(ThesisEvent e) {
        feedService.createThesisFeed(e.thesis, 'delete', e.user)
    }

    @Listener(topic = "thesisUpdated")
    void thesisUpdated(ThesisEvent e) {
        def feed = feedService.createThesisFeed(e.thesis, 'update', e.user)

        def subscribers = Subscription.findAllByArticle(e.thesis)*.subscriber.unique()
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        def subject = messageSource.getMessage('mail.thesis.update.subject', [e.thesis.id].toArray(), LCH.locale)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)
        subscriptionService.notifyAll(filteredSubscribers, subject, body)
    }
}
