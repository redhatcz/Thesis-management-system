package com.redhat.theses.listeners

import com.redhat.theses.RoleRequest
import com.redhat.theses.Feed
import com.redhat.theses.events.RoleRequestEvent
import com.redhat.theses.util.Util
import com.redhat.theses.auth.User
import com.redhat.theses.auth.Role
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

class RoleRequestListenerService {

    def messageSource
    /**
     * Dependency injection of com.redhat.theses.SubscriptionService
     */
    def subscriptionService

    /**
     * Dependency injection of org.codehaus.groovy.grails.web.mapping.LinkGenerator
     */
    def grailsLinkGenerator

    /**
     * Creates new feed and notifies administrator about creation of new Role Request
     */
    @Listener(topic = "RoleRequestCreated")
    def roleRequestCreated(RoleRequestEvent e) {
        def request = e.request
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
                grailsLinkGenerator.link(controller: 'roleRequest', action: 'show', id: request.id, absolute: true)
        ]

        def feed = new Feed(messageCode: 'feed.request.created', args: args, user: e.user).save()

        for (User admin : User.executeQuery("from User u inner join u.roles roles where roles = :role", [role: Role.ADMIN])) {
            subscriptionService.notify(admin, feed, e.user.fullName)
        }    
    }

    /**
     * Creates new feed and notifies applicant about approval of their Role Request
     */
    @Listener(topic = "RoleRequestApproved")
    def roleRequestApproved(RoleRequestEvent e) {
        def request = RoleRequest.get(e.request.id)
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
        ]

        def feed = new Feed(messageCode: 'feed.request.approved', args: args, user: e.user).save()

        subscriptionService.notify(request.applicant, feed, messageSource.getMessage('request.subject.approved', [].toArray(), LCH.locale))
    }

    /**
     * Creates new feed and notifies applicant that their Role Request has been declined
     */
    @Listener(topic = "RoleRequestDeclined")
    def roleRequestDeclined(RoleRequestEvent e) {
        def request = RoleRequest.get(e.request.id)
        def args = [
                e.user.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.user.id, absolute: true),
        ]

        def feed = new Feed(messageCode: 'feed.request.declined', args: args, user: e.user).save()

        subscriptionService.notify(request.applicant, feed, messageSource.getMessage('request.subject.declined', [].toArray(), LCH.locale))
    }
}
