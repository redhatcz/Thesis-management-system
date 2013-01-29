package com.redhat.theses.listeners

import com.redhat.theses.events.TopicEvent
import com.redhat.theses.Subscription
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author vdedik@redhat.com
 */
class TopicListenerService{

    /*
     * Dependency injection of FeedService
     */
    def feedService

    /*
     * Dependency injection of SubscriptionService
     */
    def subscriptionService

    @Listener(topic = "topicCreated")
    void topicCreated(TopicEvent e) {
        feedService.createTopicFeed(e.topic, 'insert', e.user)

        def subscribers = [e.topic.owner].unique()
        subscribers.each { subscriber ->
            subscriptionService.subscribe(subscriber, e.topic)

            if (e.user != subscriber) {
                subscriptionService.notify(subscriber,
                        "You have been automatically subscribed for topic ${e.topic.title}",
                        "User ${e?.user?.fullName} created topic ${e.topic.title}")
            }
        }
    }

    //TODO: send all subscribers notification about deletion
    @Listener(topic = "topicDeleted")
    void topicDeleted(TopicEvent e) {
        feedService.createTopicFeed(e.topic, 'delete', e.user)
    }

    @Listener(topic = "topicUpdated")
    void topicUpdated(TopicEvent e) {
        feedService.createTopicFeed(e.topic, 'update', e.user)

        def subscribers = Subscription.findAllByArticle(e.topic)*.subscriber.unique()
        //remove currentUser from subscribers
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        subscriptionService.notifyAll(filteredSubscribers,
                "Topic ${e.topic.title} has been updated",
                "User ${e?.user?.fullName} updated topic ${e.topic.title}.")
    }
}