package com.redhat.theses

import com.redhat.theses.auth.User
import org.springframework.dao.DataIntegrityViolationException

class SubscriptionService {

    Boolean subscribe(User subscriber, Article article) {
        def subscription = Subscription.findBySubscriberAndArticle(subscriber, article)
        def success = !subscription && new Subscription(subscriber: subscriber, article: article).save()
        success
    }

    Boolean unsubscribe(User subscriber, Article article) {
        def subscription = Subscription.findBySubscriberAndArticle(subscriber, article)
        def success = subscription && delete(subscription)
        success
    }

    private Boolean delete(entity) {
        def success
        try {
            entity.delete()
            success = true
        } catch (DataIntegrityViolationException e) {
            success = false
        }
        success
    }
}
