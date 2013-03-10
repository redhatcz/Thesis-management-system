package com.redhat.theses.listeners

import com.redhat.theses.auth.User
import com.redhat.theses.events.UserCreatedEvent
import grails.events.Listener

/**
 * @author vdedik@redhat.com
 */
class UserListenerService {

    /**
     * Dependency injection of com.grailsrocks.emailconfirmation.EmailConfirmationService
     */
    def emailConfirmationService

    @Listener(topic = "userCreated")
    def userCreated(UserCreatedEvent e) {
        log.info "User with email ${e.user.email} created."

        // send confirmation email
        def subject = "You have signed up for Theses management system, please confirm your registration"
        if (e.createdByAdmin) {
            subject = "You have been signed up for Theses management system, please confirm your registration"
        }
        emailConfirmationService.sendConfirmation(
            to: e.user.email,
            subject: subject,
            view: '/emailConfirmation/registrationConfirm',
            model: [fullName: e.user.fullName, password: e.password],
            id: e.user.id
        )

        log.info "Confirmation email for user ${e.user.email} with id ${e.user.id} send."
    }

    @Listener(topic ='confirmed', namespace ='plugin.emailConfirmation')
    def userConfirmed(info) {
        // set the user enabled
        def user = User.get(info.id)
        user.enabled = true
        user.save(flush: true)

        log.info "User ${info.email} successfully confirmed with application id data ${info.id}"
        return [controller:'registration', action:'confirmed']
    }

    @Listener(topic ='timeout', namespace = 'plugin.emailConfirmation')
    def userConfirmationTimedOut(info) {
        log.info "User with ${info.email} failed to confirm, the token in their link was ${info.token}"
        // delete the user
        User.get(info.id).delete()
    }

    @Listener(topic ='invalid', namespace = 'plugin.emailConfirmation')
    def userConfirmationWasInvalid(info) {
        log.info "User ${info.email} failed to confirm for application id data ${info.id}"
        return [controller:'registration', action:'expired']
    }
}
