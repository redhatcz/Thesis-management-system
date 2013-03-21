package com.redhat.theses.listeners
import com.redhat.theses.Feed
import com.redhat.theses.events.CommentEvent
import com.redhat.theses.util.Util
import grails.events.Listener
/**
 * @author vdedik@redhat.com
 */
class CommentListenerService {

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

    @Listener(topic = "commentCreated")
    def commentCreated(CommentEvent e) {
        def user = e.comment.user
        def article = e.comment.article
        def className = article.class.simpleName.toLowerCase()
        def args = [
                user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: user.id, absolute: true),
                article.title,
                grailsLinkGenerator.link(controller: className, action: 'show', id: article.id,
                        params: [headline: Util.hyphenize(article.title)], fragment: 'comments', absolute: true)
        ]

        def feed = new Feed(messageCode: 'feed.comment.created', user: user, args: args).save()

        subscriptionService.notifyAll(e.comment.article, user, feed)
    }
}
