package com.redhat.theses.auth

import com.redhat.theses.util.Util
import com.redhat.theses.AppStatus
import com.redhat.theses.RoleRequest

class RegistrationController {

    static allowedMethods = [register: "POST"]

    /**
     * Dependency injection of com.redhat.theses.auth.UserService
     */
    def userService

    /**
     * Dependency injection of com.redhat.theses.config.Configuration
     */
    def configuration

    def index() {

        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/registration', permanent: true
            return
        }

        [registrationCommand: new RegistrationCommand(), config: configuration.getConfig()]
    }

    def register(RegistrationCommand registrationCommand) {
        User user = new User(params.registrationCommand)
        user.accountExpired = false
        user.enabled = false
        user.accountLocked = false
        user.passwordExpired = false
        user.roles = [Role.STUDENT]

        if (!Util.hasAnyDomain(user.email, configuration.emailDomains)) {
            registrationCommand.errors.rejectValue('email', g.message(code: 'registration.not.allowed.email'))
        }

        if (Util.hasAnyDomain(user.email, configuration.defaultSupervisors)) {
            user.roles.add(Role.SUPERVISOR)
        }

        if (Util.hasAnyDomain(user.email, configuration.defaultLeaders)) {
            user.roles.add(Role.OWNER)
        }

        if (Util.hasAnyDomain(user.email, configuration.defaultAdmins)) {
            user.roles.add(Role.ADMIN)
        }

        if (registrationCommand.hasErrors() || !userService.save(user)) {
            render(view: "index", model: [registrationCommand: registrationCommand, config: configuration.getConfig()])
            return
        }

        def createRoleRequest = params.supervisorRequest && !Util.hasAnyDomain(user.email, configuration.defaultSupervisors)

        if (createRoleRequest) {
            new RoleRequest(applicant: user, status: AppStatus.PENDING, enabled: false).save()
        }

        log.info("User ${user.email} registration completed.")

        redirect(action: 'complete', params: [isSupervisor: createRoleRequest])
    }

    def complete() {
        def content = message(code: 'registration.complete.message')

        if (params.isSupervisor == "true") {
            content += "</br></br>" + message(code: 'registration.complete.request.message')
        }

        render view: '/emailConfirmation/lifecycle', model: [
                redirect: false,
                title: message(code: 'registration.complete.title'),
                header: message(code: 'registration.complete.header'),
                content: content
        ]
    }

    def confirmed() {
        render view: '/emailConfirmation/lifecycle', model: [
                redirect: true,
                title: message(code: 'registration.confirmed.title'),
                header: message(code: 'registration.confirmed.header'),
                content: message(code: 'registration.confirmed.message')
        ]
    }
}
