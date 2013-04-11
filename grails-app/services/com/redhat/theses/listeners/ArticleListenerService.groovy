package com.redhat.theses.listeners

import com.redhat.theses.Article
import com.redhat.theses.Feed
import com.redhat.theses.events.ArticleEvent
import com.redhat.theses.util.Util
import grails.events.Listener
/**
 * @author vdedik@redhat.com
 */
class ArticleListenerService{

    /**
     * Dependency injection of com.redhat.theses.SubscriptionService
     */
    def subscriptionService

    /**
     * Dependency injection of org.codehaus.groovy.grails.web.mapping.LinkGenerator
     */
    def grailsLinkGenerator

    /**
     * Creates new feed, subscribes all extra subscribers (usually the owner of supervisor of a topic/thesis)
     * and notifies the subscribers
     */
    @Listener(topic = "articleCreated")
    void articleCreated(ArticleEvent e) {
        def className = e.article.class.simpleName.toLowerCase()
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                e.article.title,
                grailsLinkGenerator.link(controller: className, action: 'show', id: e.article.id,
                        params: [headline: Util.hyphenize(e.article.title)], absolute: true)
        ]

        // subscribe extra subscribers
        e.extraSubscribers.unique().each {
            subscriptionService.subscribe(it, Article.get(e.article.id))
        }

        def feed = new Feed(messageCode: "feed.${className}.created", user: e.user, args: args).save()
        subscriptionService.notifyAll(e.article, e.user, feed)
    }

    /**
     * Creates new feed about deletion of an article and notifies subscribers
     */
    @Listener(topic = "articleDeleted")
    void articleDeleted(ArticleEvent e) {
        def className = e.article.class.simpleName.toLowerCase()
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                e.article.title
        ]

        def feed = new Feed(messageCode: "feed.${className}.deleted", user: e.user, args: args).save()
        subscriptionService.notifyAll(e.extraSubscribers, e.user, feed, e.article.title)
    }

    /**
     * Creates new feed about update of an article and notifies subscribers
     */
    @Listener(topic = "articleUpdated")
    void articleUpdated(ArticleEvent e) {
        def className = e.article.class.simpleName.toLowerCase()
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                e.article.title,
                grailsLinkGenerator.link(controller: className, action: 'show', id: e.article.id,
                        params: [headline: Util.hyphenize(e.article.title)], absolute: true)
        ]

        def feed = new Feed(messageCode: "feed.${className}.updated", user: e.user, args: args).save()
        subscriptionService.notifyAll(e.article, e.user, feed)
    }
}