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
     * Dependency injection of MessageSource
     */
    def messageSource

    @Listener(topic = "topicCreated")
    void topicCreated(TopicEvent e) {
        feedService.createTopicFeed(e.topic, 'insert', e.user)
    }

    @Listener(topic = "topicDeleted")
    void topicDeleted(TopicEvent e) {
        feedService.createTopicFeed(e.topic, 'delete', e.user)
    }

    @Listener(topic = "topicUpdated")
    void topicUpdated(TopicEvent e) {
        def feed = feedService.createTopicFeed(e.topic, 'update', e.user)

        def subscribers = Subscription.findAllByArticle(e.topic)*.subscriber.unique()
        //remove currentUser from subscribers
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        filteredSubscribers.each {
            //TODO: send email instead of printing the message to stdout, and also refactoring needed
            println "Sending mail to subscriber ${it.fullName}:"
            println messageSource.getMessage(feed.messageCode, feed.args.toArray(), LocaleContextHolder.getLocale())
        }
    }
}