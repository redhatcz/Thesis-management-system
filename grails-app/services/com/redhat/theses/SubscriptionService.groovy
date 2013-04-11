package com.redhat.theses
import com.redhat.theses.auth.User
import com.redhat.theses.util.Commons

class SubscriptionService {

    /**
     * Dependency injection of org.springframework.context.MessageSource
     */
    def messageSource

    /**
     * Subscribes a subscriber for an article
     *
     * @return true on success, false otherwise
     */
    Boolean subscribe(User subscriber, Article article) {
        def subscription = Subscription.findBySubscriberAndArticle(subscriber, article)
        def success = !subscription && new Subscription(subscriber: subscriber, article: article).save()
        success
    }

    /**
     * Unsubscribes a subscriber from an article
     *
     * @return true on success, false otherwise
     */
    Boolean unsubscribe(User subscriber, Article article) {
        def subscription = Subscription.findBySubscriberAndArticle(subscriber, article)
        def success = subscription && Commons.delete(subscription)
        success
    }

    /**
     * Notifies a subscriber about an action
     */
    def notify(User subscriber, Feed feed, String articleTitle) {
        //Setting english locale as the subscriber's locale is not stored anywhere
        def subj = messageSource.getMessage("mail.subscription.subject", [articleTitle].toArray(), Locale.ENGLISH)
        def body = messageSource.getMessage(feed.messageCode, feed.args.toArray(), Locale.ENGLISH)

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
            log.debug "Mail to ${subscriber.email} sent"
        } catch (Exception ex) {
            log.error ex.getMessage()
        }
    }

    /**
     * Notifies all subscribers of an article excluding current user
     */
    def notifyAll(Article article, User currentUser, Feed feed) {
        def subscribers = Subscription.findAllByArticle(article)*.subscriber
        notifyAll(subscribers, currentUser, feed, article.title)
    }

    /**
     * Notifies the list of subscribers excluding current user
     */
    def notifyAll(List<User> subscribers, User currentUser, Feed feed, String articleTitle) {
        subscribers.unique().each { subscriber ->
            if (subscriber.id != currentUser.id && subscriber.sendMail) {
                notify(subscriber, feed, articleTitle)
            }
        }
    }
}
