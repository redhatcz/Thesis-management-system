package com.redhat.theses.auth

class RegistrationController {

    static allowedMethods = [register: "POST"]

    def index() {
        def requestURI = request.requestURI
        def actionFromUrl = requestURI.substring(requestURI.lastIndexOf('/') + 1, requestURI.length() - 9)

        if (actionFromUrl == 'index') {
            redirect uri: '/registration', permanent: true
        } else {
            [registrationCommand: new RegistrationCommand()]
        }
    }

    def register(RegistrationCommand registrationCommand) {
        //TODO: set default Authority
        def user = new User(params.registrationCommand)
        user.accountExpired = false
        user.enabled = false
        user.accountLocked = false
        user.passwordExpired = false

        if (registrationCommand.hasErrors() || !user.save(flush: true)) {
            render(view: "index", model: [registrationCommand: registrationCommand])
            return
        }

        flash.message = message(code: 'registration.sucessfull.message')
        redirect(url: "/")
    }
}
