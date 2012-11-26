package com.redhat.theses.auth

import com.redhat.theses.util.Util
import com.redhat.theses.Membership

class ProfileController {

    static allowedMethods = [updatePassword: 'POST']

    def springSecurityService

    def userService

    def index() {
        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/profile', permanent: true
            return
        }

        if (!springSecurityService.isLoggedIn()) {
            flash.message = message code: 'profile.index.error.notLoggedIn',
                    default: 'You must be logged in to view your profile'
            redirect uri: '/'
            return
        }

        def userInstance = springSecurityService.currentUser

        render view: 'show',
               model: [userInstance: userInstance, memberships: Membership.findAllByUser(userInstance)]
    }

    def editPassword() {
        if (!springSecurityService.isLoggedIn()) {
            flash.message = message code: 'profile.index.error.notLoggedIn',
                    default: 'You must be logged in to view your profile'
            redirect uri: '/'
            return
        }

        [passwordCommand: new PasswordCommand()]
    }

    def updatePassword(PasswordCommand passwordCommand) {
        if (!springSecurityService.isLoggedIn()) {
            flash.message = message code: 'profile.index.error.notLoggedIn',
                    default: 'You must be logged in to view your profile'
            redirect uri: '/'
            return
        }

        def user = springSecurityService.currentUser
        def newPassword = passwordCommand.password

        if (passwordCommand.hasErrors() || !userService.updatePassword(user, newPassword)) {
            render view: 'editPassword', model: [passwordCommand: passwordCommand]
            return
        }

        flash.message = message(code: 'profile.updatePassword.sucessfull.message',
                default: 'Your password was successfully updated.')
        redirect controller: 'profile'
    }
}
