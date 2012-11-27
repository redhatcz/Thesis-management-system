package com.redhat.theses

class SubscriptionController {

    def springSecurityService

    def subscriptionService

    //TODO: getHeader('Referer') is not 100% safe, we should consider alternative
    def subscribe(Long articleId) {
        def article = Article.get(articleId)
        if (!subscriptionService.subscribe(springSecurityService.currentUser, article)) {
            flash.message = g.message code: 'subscription.subscribe.error',
                    default: 'The subscription was unsuccessfull'
            redirect url: request.getHeader('referer')
            return
        }

        flash.message = g.message code: 'subscription.subscribe.success',
                default: 'You successfully subscribed for this article'
        redirect url: request.getHeader('referer')
    }

    //TODO: getHeader('Referer') is not 100% safe, we should consider alternative
    def unsubscribe(Long articleId) {
        def article = Article.get(articleId)
        if (!subscriptionService.unsubscribe(springSecurityService.currentUser, article)) {
            flash.message = g.message code: 'subscription.unsubscribe.error',
                    default: 'The unsubscription was unsuccessfull'
            redirect url: request.getHeader('referer')
            return
        }

        flash.message = g.message code: 'subscription.unsubscribe.success',
                default: 'You successfully unsubscribed from this article'
        redirect url: request.getHeader('referer')
    }
}
