package com.redhat.theses.auth

import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ProfileController {

    static allowedMethods = [update: 'POST']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    def index() {
        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/profile', permanent: true
            return
        }

        def userInstance = springSecurityService.currentUser

        redirect controller: 'user', action: 'show', id: userInstance.id
    }

    def edit() {
        def user = springSecurityService.currentUser
        def profileCommand = new ProfileCommand()
        profileCommand.email = user.email
        profileCommand.fullName = user.fullName

        [profileCommand: profileCommand, userInstance: user]
    }

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

        user.email = profileCommand.email
        user.fullName = profileCommand.fullName
        if (profileCommand.password) {
            user.password = profileCommand.password
        }

        if (profileCommand.hasErrors() || !user.save(flush: true)) {
            render view: 'edit', model: [profileCommand: profileCommand, userInstance: user]
            return
        }

        springSecurityService.reauthenticate(user.email, user.password)
        flash.message = message(code: 'profile.updated')
        redirect action: 'index'
    }
}
