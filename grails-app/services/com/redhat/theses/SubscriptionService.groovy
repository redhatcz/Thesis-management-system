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
}
