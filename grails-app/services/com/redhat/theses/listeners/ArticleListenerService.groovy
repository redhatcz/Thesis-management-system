package com.redhat.theses.listeners
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
            subscriptionService.subscribe(it, e.article)
        }

        def feed = new Feed(messageCode: "feed.${className}.insert", user: e.user, args: args).save()
        subscriptionService.notifyAll(e.article, e.user, feed)
    }

    @Listener(topic = "articleDeleted")
    void articleDeleted(ArticleEvent e) {
        def className = e.article.class.simpleName.toLowerCase()
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                e.article.title
        ]

        def feed = new Feed(messageCode: "feed.${className}.delete", user: e.user, args: args).save()
        subscriptionService.notifyAll(e.extraSubscribers, e.user, feed, e.article.title)
    }

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

        def feed = new Feed(messageCode: "feed.${className}.update", user: e.user, args: args).save()
        subscriptionService.notifyAll(e.article, e.user, feed)
    }
}