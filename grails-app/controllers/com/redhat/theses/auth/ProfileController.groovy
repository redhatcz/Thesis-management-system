package com.redhat.theses.auth

import com.redhat.theses.util.Util
import com.redhat.theses.Membership

class ProfileController {

    static allowedMethods = [updatePassword: 'POST']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.auth.UserService
     */
    def userService

    def index() {
        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/profile', permanent: true
            return
        }

        if (!springSecurityService.isLoggedIn()) {
            flash.message = message(code: 'profile.not.authenticated')
            redirect uri: '/'
            return
        }

        def userInstance = springSecurityService.currentUser

        render view: 'show',
               model: [userInstance: userInstance, memberships: Membership.findAllByUser(userInstance)]
    }

    def editPassword() {
        if (!springSecurityService.isLoggedIn()) {
            flash.message = message(code: 'profile.not.authenticated')
            redirect uri: '/'
            return
        }

        [passwordCommand: new PasswordCommand()]
    }

    def updatePassword(PasswordCommand passwordCommand) {
        if (!springSecurityService.isLoggedIn()) {
            flash.message = message(code: 'profile.not.authenticated')
            redirect uri: '/'
            return
        }

        def user = springSecurityService.currentUser
        def newPassword = passwordCommand.password

        if (passwordCommand.hasErrors() || !userService.updatePassword(user, newPassword)) {
            render view: 'editPassword', model: [passwordCommand: passwordCommand]
            return
        }

        flash.message = message(code: 'profile.password.updated')
        redirect controller: 'profile'
    }
}
