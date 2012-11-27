package com.redhat.theses.listeners

import org.springframework.context.ApplicationListener
import com.redhat.theses.Topic
import com.redhat.theses.events.TopicCreatedEvent
import com.redhat.theses.events.TopicUpdatedEvent
import com.redhat.theses.events.TopicDeletedEvent
import com.redhat.theses.Subscription
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author vdedik@redhat.com
 */
class TopicUpdatedListenerService implements ApplicationListener<TopicUpdatedEvent> {
    def feedService

    def messageSource

    @Override
    void onApplicationEvent(TopicUpdatedEvent e) {
        def feed = feedService.createTopicFeed((Topic) e.source, 'update', e.currentUser)

        def subscribers = Subscription.findAllByArticle(e.source)*.subscriber.unique()
        //remove currentUser from subscribers
        def filteredSubscribers = subscribers.findAll {it.id != e.currentUser.id}

        filteredSubscribers.each {
            //TODO: send email instead of printing the message to stdout, and also refactoring needed
            println "Sending mail to subscriber ${it.fullName}:"
            println messageSource.getMessage(feed.messageCode, feed.args.toArray(), LocaleContextHolder.getLocale())
        }
    }
}
