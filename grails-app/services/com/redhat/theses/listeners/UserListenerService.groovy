package com.redhat.theses.listeners

import com.redhat.theses.auth.User
import com.redhat.theses.events.EmailChangedEvent
import com.redhat.theses.events.UserCreatedEvent
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

/**
 * @author vdedik@redhat.com
 */
class UserListenerService {

    /**
     * Dependency injection of com.grailsrocks.emailconfirmation.EmailConfirmationService
     */
    def emailConfirmationService

    /**
     * Dependency injection of org.springframework.context.MessageSource
     */
    def messageSource

    @Listener(topic = "userCreated")
    def userCreated(UserCreatedEvent e) {
        log.debug "User with email ${e.user.email} created."

        // send confirmation email
        def subject = messageSource.getMessage('mail.registration.confirmation.subject', [].toArray(), LCH.locale)
        if (e.createdByAdmin) {
            subject = messageSource.getMessage('mail.registration.by.admin.confirmation.subject', [].toArray(), LCH.locale)
        }
        emailConfirmationService.sendConfirmation(
            to: e.user.email,
            subject: subject,
            view: '/emailConfirmation/registrationConfirm',
            model: [fullName: e.user.fullName, password: e.password],
            id: e.user.id,
            event: "userCreated"
        )

        log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} send."
    }

    @Listener(topic ='userCreated.confirmed', namespace ='plugin.emailConfirmation')
    def userConfirmed(info) {
        // set the user enabled
        def user = User.get(info.id)
        user.enabled = true
        user.save(flush: true)

        log.debug "User ${info.email} successfully confirmed with application id data ${info.id}"
        return [controller:'registration', action:'confirmed']
    }

    @Listener(topic ='userCreated.timeout', namespace = 'plugin.emailConfirmation')
    def userConfirmationTimedOut(info) {
        log.debug "User with ${info.email} failed to confirm, the token in their link was ${info.token}"
        // delete the user
        User.get(info.id).delete()
    }

    @Listener(topic ='userCreated.invalid', namespace = 'plugin.emailConfirmation')
    def userConfirmationWasInvalid(info) {
        log.debug "User ${info.email} failed to confirm for application id data ${info.id}"
        return [controller:'registration', action:'expired']
    }

    @Listener(topic = 'emailChanged')
    def emailChanged(EmailChangedEvent e) {
        log.debug "User with email ${e.user.email} changing email."

        // send confirmation email
        def subject = messageSource.getMessage('mail.email.confirmation.subject', [].toArray(), LCH.locale)

        emailConfirmationService.sendConfirmation(
                to: e.email,
                subject: subject,
                view: '/emailConfirmation/emailConfirm',
                model: [email: e.email],
                id: e.user.id,
                event: "emailChanged"
        )

        log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} send."
    }

    @Listener(topic ='emailChanged.confirmed', namespace ='plugin.emailConfirmation')
    def userConfirmedEmail(info) {
        // set the user enabled
        def user = User.get(info.id)
        user.email = info.email
        user.save(flush: true)

        log.debug "User ${info.email} successfully confirmed email with application id data ${info.id}"
        return [controller:'profile', action:'emailConfirmed']
    }

    @Listener(topic ='emailChanged.invalid', namespace = 'plugin.emailConfirmation')
    def userConfirmationOfEmailWasInvalid(info) {
        log.debug "User ${info.email} failed to confirm email for application id data ${info.id}"
        return [controller:'profile', action:'emailConfirmationExpired']
    }
}
