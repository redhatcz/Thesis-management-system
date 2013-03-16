package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Commons
import org.springframework.context.i18n.LocaleContextHolder as LCH

class SubscriptionService {

    /**
     * Dependency injection of org.springframework.context.MessageSource
     */
    def messageSource

    Boolean subscribe(User subscriber, Article article) {
        def subscription = Subscription.findBySubscriberAndArticle(subscriber, article)
        def success = !subscription && new Subscription(subscriber: subscriber, article: article).save()
        success
    }

    Boolean unsubscribe(User subscriber, Article article) {
        def subscription = Subscription.findBySubscriberAndArticle(subscriber, article)
        def success = subscription && Commons.delete(subscription)
        success
    }

    def notify(User subscriber, Feed feed, String articleTitle) {
        def subj = messageSource.getMessage("mail.subscription.subject", [articleTitle].toArray(), LCH.locale)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), LCH.locale)

        // create notification
        new Notification(user: subscriber, feed: feed).save()
        // send mail
        log.debug "Sending mail to ${subscriber.email}"
        try {
            sendMail {
                to subscriber.email
                subject subj
                html body
            }
        } catch (Exception ex) {
            log.error ex.getMessage()
        }
        log.debug "Mail to ${subscriber.email} sent"
    }

    def notifyAll(Article article, User currentUser, Feed feed) {
        def subscribers = Subscription.findAllByArticle(article)*.subscriber
        notifyAll(subscribers, currentUser, feed, article.title)
    }

    def notifyAll(List<User> subscribers, User currentUser, Feed feed, String articleTitle) {
        subscribers.unique().each { subscriber ->
            if (subscriber.id != currentUser.id) {
                notify(subscriber, feed, articleTitle)
            }
        }
    }
}
