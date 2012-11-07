package com.redhat.theses.auth

import com.redhat.theses.University
import com.redhat.theses.Membership

class RegistrationController {

    static allowedMethods = [register: "POST"]

    def userService

    def index() {
        def requestURI = request.requestURI
        def actionFromUrl = requestURI.substring(requestURI.lastIndexOf('/') + 1, requestURI.length() - 9)

        if (actionFromUrl == 'index') {
            redirect uri: '/registration', permanent: true
        } else {
            [registrationCommand: new RegistrationCommand(), universityList: University.findAll()]
        }
    }

    def register(RegistrationCommand registrationCommand) {
        //TODO: set default Authority
        User user = new User(params.registrationCommand)
        user.accountExpired = false
        user.enabled = false
        user.accountLocked = false
        user.passwordExpired = false

        Membership membership = new Membership(user: user, organization: registrationCommand.university)

        if (registrationCommand.hasErrors() || !userService.saveWithMemberships(user, [membership])) {
            render(view: "index", model: [registrationCommand: registrationCommand, universityList: University.findAll()])
            return
        }

        flash.message = message(code: 'registration.sucessfull.message')
        redirect(url: "/")
    }
}
