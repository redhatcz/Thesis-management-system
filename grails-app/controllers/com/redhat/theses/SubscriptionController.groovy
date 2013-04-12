package com.redhat.theses

import grails.plugins.springsecurity.Secured

@Secured(['isAuthenticated()'])
class SubscriptionController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.SubscriptionService
     */
    def subscriptionService

    //TODO: getHeader('Referer') is not 100% safe, we should consider alternative
    def subscribe(Long articleId) {
        def article = Article.get(articleId)
        if (!subscriptionService.subscribe(springSecurityService.currentUser, article)) {
            flash.message = g.message code: 'subscription.not.subscribed'
            redirect url: request.getHeader('referer')
            return
        }

        flash.message = g.message code: 'subscription.subscribed'
        redirect url: request.getHeader('referer')
    }

    //TODO: getHeader('Referer') is not 100% safe, we should consider alternative
    def unsubscribe(Long articleId) {
        def article = Article.get(articleId)
        if (!subscriptionService.unsubscribe(springSecurityService.currentUser, article)) {
            flash.message = g.message code: 'subscription.not.unsubscribed'
            redirect url: request.getHeader('referer')
            return
        }

        flash.message = g.message code: 'subscription.unsubscribed'
        redirect url: request.getHeader('referer')
    }
}
