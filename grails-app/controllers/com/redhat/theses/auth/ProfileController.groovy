package com.redhat.theses.auth

import com.redhat.theses.util.Util
import com.redhat.theses.Membership
import com.redhat.theses.MembershipsCommand
import com.redhat.theses.Organization

class ProfileController {

    def springSecurityService

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
}
