package com.redhat.theses.listeners
import com.redhat.theses.Feed
import com.redhat.theses.Subscription
import com.redhat.theses.events.CommentEvent
import com.redhat.theses.util.Util
import org.springframework.context.i18n.LocaleContextHolder as LCH
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
        def articleType = article.class.simpleName.toLowerCase()
        def args = [
                user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: user.id, absolute: true),
                article.title,
                grailsLinkGenerator.link(controller: articleType, action: 'show', id: article.id,
                        params: [headline: Util.hyphenize(article.title)], fragment: 'comments', absolute: true)
        ]

        def feed = new Feed(messageCode: 'feed.comment.insert', user: user, args: args).save()

        def filteredSubscribers = Subscription.findAllByArticle(article)*.subscriber.findAll {it.id != user.id}
        subscriptionService.notifyAll(
                filteredSubscribers,
                messageSource.getMessage("mail.${articleType}.comment.created.subject", [article.title].toArray(), LCH.locale),
                messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)
        )
    }
}
