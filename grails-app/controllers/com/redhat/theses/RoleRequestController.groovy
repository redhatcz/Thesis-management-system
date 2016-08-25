package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.auth.Role
import com.redhat.theses.events.RoleRequestEvent
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class RoleRequestController {

    /**
     * Dependency injection of com.redhat.theses.FilterService
     */
    def filterService

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    static defaultAction = "list"

    @Secured(['ROLE_ADMIN'])
    def list() {
        if (!params.filter) {
            params.filter = [:]
        }
        if (!params?.filtering) {
            params.filter << [status: AppStatus.PENDING]
        }
        else {
            params.type = [applicant: [fullName: "ilike"]]
        }
        params.filter << [enabled: true]

        def requestInstanceList = filterService.filter(params, RoleRequest)
        def requestInstanceTotal = filterService.count(params, RoleRequest)

        [requestInstanceList: requestInstanceList, requestInstanceTotal: requestInstanceTotal]
    }

    def show(Integer id) {
        def requestInstance = RoleRequest.get(id)

        if (!requestInstance) {
            flash.message = message(code: 'request.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def user = springSecurityService.currentUser

        if (!SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') && user != requestInstance.applicant) {
            flash.message = message(code: 'security.denied.message')
            redirect(action: 'list')
            return
        }

        def canManage = SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')

        [requestInstance: requestInstance, canManage: canManage]
    }

    @Secured('ROLE_ADMIN')
    def decline(Long id) {
        def requestInstance = RoleRequest.get(id)
        if (!requestInstance) {
            flash.message = message(code: 'request.not.found', args: [id])
            redirect(action: 'list')
            return
        }

        User user = springSecurityService.currentUser

        if (!SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {

            flash.message = message(code: 'security.denied.message')
            redirect(action: 'list')
            return
        }

        requestInstance.status = AppStatus.DECLINED

        if (!requestInstance.save()) {
            flash.message = message(code: 'request.save.error')
            redirect(uri:'/')
            return
        }

        event("RoleRequestDeclined", new RoleRequestEvent(requestInstance, user))
        flash.message = message(code: 'request.declined', args: [id])
        redirect(action: 'list')
    }

    @Secured('ROLE_ADMIN')
    def approve(Long id) {
        def requestInstance = RoleRequest.get(id)
        if (!requestInstance) {
            flash.message = message(code: 'request.not.found', args: [id])
            redirect(action: 'list')
            return
        }

        User user = springSecurityService.currentUser

        if (!SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {

            flash.message = message(code: 'security.denied.message')
            redirect(action: 'list')
            return
        }

        requestInstance.status = AppStatus.APPROVED

        requestInstance.applicant.roles.add(Role.SUPERVISOR)

        if (!requestInstance.save() || !requestInstance.applicant.save()) {
            flash.message = message(code: 'request.save.error')
            redirect(uri:'/')
            return
        }

        event("RoleRequestApproved", new RoleRequestEvent(requestInstance, user))
        flash.message = message(code: 'request.approved', args: [id])
        redirect(action: 'list')
    }
}
