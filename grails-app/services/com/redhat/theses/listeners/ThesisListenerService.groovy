package com.redhat.theses.listeners

import com.redhat.theses.Feed
import com.redhat.theses.Subscription
import com.redhat.theses.events.ThesisEvent
import com.redhat.theses.util.Util
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

/**
 * @author vdedik@redhat.com
 */
class ThesisListenerService {

    /**
     * Dependency injection of com.redhat.theses.SubscriptionService
     */
    def subscriptionService

    /**
     * Dependency injection of org.springframework.context.MessageSource
     */
    def messageSource

    /**
     * Dependency injection of org.codehaus.groovy.grails.web.mapping.LinkGenerator
     */
    def grailsLinkGenerator

    @Listener(topic = "thesisCreated")
    void thesisCreated(ThesisEvent e) {
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                grailsLinkGenerator.link(controller: 'thesis', action: 'show', id: e.thesis.id,
                        params: [headline: Util.hyphenize(e.thesis.title)], absolute: true),
                e.thesis.topic.title,
                grailsLinkGenerator.link(controller: 'topic', action: 'show', id: e.thesis.topic.id,
                        params: [headline: Util.hyphenize(e.thesis.topic.title)], absolute: true)
        ]

        def feed = new Feed(messageCode: "feed.thesis.insert", user: e.user, args: args).save()

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
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                e.thesis.topic.title,
                grailsLinkGenerator.link(controller: 'topic', action: 'show', id: e.thesis.topic.id,
                        params: [headline: Util.hyphenize(e.thesis.topic.title)], absolute: true)
        ]

        new Feed(messageCode: "feed.thesis.delete", user: e.user, args: args).save()
    }

    @Listener(topic = "thesisUpdated")
    void thesisUpdated(ThesisEvent e) {
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                grailsLinkGenerator.link(controller: 'thesis', action: 'show', id: e.thesis.id,
                        params: [headline: Util.hyphenize(e.thesis.title)], absolute: true),
                e.thesis.topic.title,
                grailsLinkGenerator.link(controller: 'topic', action: 'show', id: e.thesis.topic.id,
                        params: [headline: Util.hyphenize(e.thesis.topic.title)], absolute: true)
        ]

        def feed = new Feed(messageCode: "feed.thesis.update", user: e.user, args: args).save()

        def subscribers = Subscription.findAllByArticle(e.thesis)*.subscriber.unique()
        def filteredSubscribers = subscribers.findAll {it.id != e.user.id}

        def subject = messageSource.getMessage('mail.thesis.update.subject', [e.thesis.id].toArray(), LCH.locale)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)
        subscriptionService.notifyAll(filteredSubscribers, subject, body)
    }
}
