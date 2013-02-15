package com.redhat.theses.auth

import com.redhat.theses.util.Util

class ProfileController {

    static allowedMethods = [updatePassword: 'POST']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

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

        redirect controller: 'user', action: 'show', id: userInstance.id
    }
}
