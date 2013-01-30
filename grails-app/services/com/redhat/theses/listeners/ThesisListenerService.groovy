package com.redhat.theses.listeners

import com.redhat.theses.Subscription
import com.redhat.theses.events.ThesisEvent
import grails.events.Listener

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

    @Listener(topic = "thesisCreated")
    void thesisCreated(ThesisEvent e) {
        feedService.createThesisFeed(e.thesis, 'insert', e.user)

        def subscribers = [e.thesis.supervisor, e.thesis.assignee].unique()
        subscribers.each { subscriber ->
            subscriptionService.subscribe(subscriber, e.thesis)

            if (e.user != subscriber) {
                subscriptionService.notify(subscriber,
                        "You have been automatically subscribed for thesis ${e.thesis.id}",
                        "User ${e?.user?.fullName} created thesis ${e.thesis.id}")
            }
        }
    }

    //TODO: send all subscribers notification about deletion
    @Listener(topic = "thesisDeleted")
    void thesisDeleted(ThesisEvent e) {
        feedService.createThesisFeed(e.thesis, 'delete', e.user)
    }

    @Listener(topic = "thesisUpdated")
    void thesisUpdated(ThesisEvent e) {
        feedService.createThesisFeed(e.thesis, 'update', e.user)

        def subscribers = Subscription.findAllByArticle(e.thesis)*.subscriber.unique()
        //remove currentUser from subscribers
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        subscriptionService.notifyAll(filteredSubscribers,
                "Thesis ${e.thesis.id} has been updated",
                "User ${e?.user?.fullName} updated thesis ${e.thesis.id}.")
    }
}
