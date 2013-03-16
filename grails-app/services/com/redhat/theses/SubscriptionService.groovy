package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Commons

class SubscriptionService {

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

    def notify(User subscriber, String subj, String body) {
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
    }

    def notifyAll(List<User> subscribers, String subj, String body) {
        subscribers.each { subscriber ->
            notify(subscriber, subj, body)
        }
    }
}
