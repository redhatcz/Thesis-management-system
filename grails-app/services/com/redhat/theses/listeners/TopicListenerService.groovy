package com.redhat.theses.listeners

import com.redhat.theses.events.TopicEvent
import com.redhat.theses.Subscription
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

/**
 * @author vdedik@redhat.com
 */
class TopicListenerService{

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

    @Listener(topic = "topicCreated")
    void topicCreated(TopicEvent e) {
        def feed = feedService.createTopicFeed(e.topic, 'insert', e.user)

        def subscribers = [e.topic.owner].unique()
        subscribers.each {
            subscriptionService.subscribe(it, e.topic)
        }
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        def subject = messageSource.getMessage('mail.topic.subscribed.subject', [e.topic.title].toArray(), LCH.locale)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)
        subscriptionService.notifyAll(filteredSubscribers, subject, body)
    }

    //TODO: send all subscribers notification about deletion
    @Listener(topic = "topicDeleted")
    void topicDeleted(TopicEvent e) {
        feedService.createTopicFeed(e.topic, 'delete', e.user)
    }

    @Listener(topic = "topicUpdated")
    void topicUpdated(TopicEvent e) {
        def feed = feedService.createTopicFeed(e.topic, 'update', e.user)

        def subscribers = Subscription.findAllByArticle(e.topic)*.subscriber.unique()
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        def subject = messageSource.getMessage('mail.topic.update.subject', [e.topic.id].toArray(), LCH.locale)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)
        subscriptionService.notifyAll(filteredSubscribers, subject, body)
    }
}