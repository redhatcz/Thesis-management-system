package com.redhat.theses.auth
import com.redhat.theses.events.EmailChangedEvent
import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

class ProfileController {

    static allowedMethods = [update: 'POST']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    @Secured(['isAuthenticated()'])
    def index() {
        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/profile', permanent: true
            return
        }

        def userInstance = springSecurityService.currentUser

        redirect controller: 'user', action: 'show', id: userInstance.id
    }

    @Secured(['isAuthenticated()'])
    def edit() {
        def user = springSecurityService.currentUser
        def profileCommand = new ProfileCommand()
        profileCommand.email = user.email
        profileCommand.fullName = user.fullName
        profileCommand.sendMail = user.sendMail

        [profileCommand: profileCommand, userInstance: user]
    }

    @Secured(['isAuthenticated()'])
    def update(ProfileCommand profileCommand) {
        Long id = params.user.long('id')
        Long version = params.user.long('version')
        def user = User.get(id)
        if (user.id != springSecurityService.currentUser.id) {
            flash.message = message(code: 'security.denied.message')
            redirect uri: '/'
            return
        }

        if (version != null && user.version > version) {
            user.errors.rejectValue("version", "user.optimistic.lock.error")
            render(view: "edit", model: [profileCommand: profileCommand, userInstance: user])
            return
        }

        user.sendMail = profileCommand.sendMail
        user.fullName = profileCommand.fullName
        if (profileCommand.password) {
            user.password = profileCommand.password
        }

        if (profileCommand.hasErrors() || !user.save(flush: true)) {
            //password will get persisted if not discarded
            user.discard()
            render view: 'edit', model: [profileCommand: profileCommand, userInstance: user]
            return
        }

        springSecurityService.reauthenticate(user.email, user.password)

        if (user.email != profileCommand.email) {
            event('emailChanged', new EmailChangedEvent(user, profileCommand.email))
            redirect action: 'emailChanged'
        } else {
            flash.message = message(code: 'profile.updated')
            redirect controller: 'user', action: 'show', id: user.id
        }
    }

    def emailChanged() {
        render view: '/emailConfirmation/lifecycle', model: [
                redirect: false,
                title: message(code: 'profile.email.changed.title'),
                header: message(code: 'profile.email.changed.header'),
                content: message(code: 'profile.email.changed.message')
        ]
    }

    def emailConfirmed() {
        render view: '/emailConfirmation/lifecycle', model: [
                redirect: false,
                title: message(code: 'profile.email.confirmed.title'),
                header: message(code: 'profile.email.confirmed.header'),
                content: message(code: 'profile.email.confirmed.message')
        ]
    }
}
