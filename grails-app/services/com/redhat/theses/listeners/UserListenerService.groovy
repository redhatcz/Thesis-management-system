package com.redhat.theses.listeners
import com.redhat.theses.auth.User
import com.redhat.theses.events.EmailChangedEvent
import com.redhat.theses.events.LostPasswordEvent
import com.redhat.theses.events.UserCreatedEvent
import grails.events.Listener
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.exception.ExceptionUtils
import org.springframework.context.i18n.LocaleContextHolder as LCH

import java.sql.SQLException

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

    /**
     * Sends confirmation mail to newly created user
     */
    @Listener(topic = "userCreated")
    def userCreated(UserCreatedEvent e) {
        log.debug "User with email ${e.user.email} created."

        // send confirmation email
        def subject = messageSource.getMessage('mail.registration.confirmation.subject', [].toArray(), LCH.locale)
        if (e.createdByAdmin) {
            subject = messageSource.getMessage('mail.registration.by.admin.confirmation.subject', [].toArray(), LCH.locale)
        }
        try {
            emailConfirmationService.sendConfirmation(
                to: e.user.email,
                subject: subject,
                view: '/emailConfirmation/message',
                model: [code: 'mail.registration.confirmation', args: [e.user.fullName, e.password]],
                id: e.user.id,
                event: "userCreated"
            )
            log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} sent."
        } catch (ex) {
            log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} not sent."
            log.error ex.message
        }


    }

    /**
     * Enables account with given id
     */
    @Listener(topic ='userCreated.confirmed', namespace ='plugin.emailConfirmation')
    def userConfirmed(info) {
        // set the user enabled
        def user = User.get(info.id)
        user.enabled = true
        try {
            user.save()
            log.info "User ${info.email} successfully confirmed with application id data ${info.id}"
        } catch (Exception ex) {
            log.error(ex.getMessage())
            log.error(ExceptionUtils.getStackTrace(ex))
            log.error(ExceptionUtils.getRootCauseMessage(ex))
            ExceptionUtils.getRootCauseStackTrace(ex).each { log.error(it) }
            def exception = ExceptionUtils.getRootCause(ex)
            if (exception instanceof SQLException) {
                log.error(exception.getNextException().getMessage())
            }
            throw ex
        }

        return [controller:'registration', action:'confirmed']
    }

    /**
     * Deletes user with given id
     */
    @Listener(topic ='userCreated.timeout', namespace = 'plugin.emailConfirmation')
    def userConfirmationTimedOut(info) {
        log.info "User with ${info.email} failed to confirm, the token in their link was ${info.token}"
        // delete the user
        try {
            User user = null
            User.withoutHibernateFilters {
                user = User.findByEmail(info.email)
            }
            user.delete()
            log.info "User with email ${info.email} deleted"
        } catch (Exception ex) {
            log.warn "User with email ${info.email} could not be deleted due to an exception. Exception message: ${ex.message}"
        }
    }

    /**
     * Invalid link
     */
    @Listener(topic ='invalid', namespace = 'plugin.emailConfirmation')
    def emailConfirmationInvalid(info) {
        log.info "User ${info.email} failed to confirm for application id data ${info.id}"
        return [controller:'emailConfirmation', action:'expired']
    }

    /**
     * Sends confirmation email to confirm email change
     */
    @Listener(topic = 'emailChanged')
    def emailChanged(EmailChangedEvent e) {
        log.debug "User with email ${e.user.email} changing email."

        // send confirmation email
        def subject = messageSource.getMessage('mail.email.confirmation.subject', [].toArray(), LCH.locale)

        try{
            emailConfirmationService.sendConfirmation(
                    to: e.email,
                    subject: subject,
                    view: '/emailConfirmation/message',
                    model: [code: 'mail.email.confirmation', args: [e.email]],
                    id: e.user.id,
                    event: "emailChanged"
            )
            log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} sent."
        } catch (ex) {
            log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} not sent."
            log.error ex.message
        }
    }

    /**
     * Sets new email for the user
     */
    @Listener(topic ='emailChanged.confirmed', namespace ='plugin.emailConfirmation')
    def userConfirmedEmail(info) {
        def user = User.get(info.id)
        user.email = info.email
        user.save(flush: true)

        log.debug "User ${info.email} successfully confirmed email with application id data ${info.id}"
        return [controller:'profile', action:'emailConfirmed']
    }

    /**
     * Sends verification email
     */
    @Listener(topic = 'lostPassword')
    def lostPassword(LostPasswordEvent e) {
        log.debug "User with email ${e.user.email} lost password."

        def subject = messageSource.getMessage('mail.lost.password.subject', [].toArray(), LCH.locale)

        try {
            emailConfirmationService.sendConfirmation(
                    to: e.user.email,
                    subject: subject,
                    view: '/emailConfirmation/message',
                    model: [code: 'mail.lost.password.verify'],
                    id: e.user.id,
                    event: "lostPassword"
            )
            log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} sent."
        } catch (ex) {
            log.debug "Confirmation email for user ${e.user.email} with id ${e.user.id} not sent."
            log.error ex.message
        }
    }

    /**
     * Sets new password and sends it to the user
     */
    @Listener(topic ='lostPassword.confirmed', namespace ='plugin.emailConfirmation')
    def lostPasswordVerificationValid(info) {
        def user = User.get(info.id)
        def password = RandomStringUtils.random(8, true, true)
        user.password = password
        user.save(flush: true)

        def subj = messageSource.getMessage('mail.new.password.set.subject', [].toArray(), LCH.locale)
        try {
            sendMail {
                to user.email
                subject subj
                text view: '/emailConfirmation/message',
                     model: [code: 'mail.new.password.set', args: [user.email, password]]
            }

            log.debug "User ${info.email} successfully verified email with application id data ${info.id}"
        } catch (Exception ex) {
            log.error ex.getMessage()
        }

        return [controller:'login', action:'lostPasswordVerified']
    }
}
