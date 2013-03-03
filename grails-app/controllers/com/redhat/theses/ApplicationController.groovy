package com.redhat.theses

import com.redhat.theses.auth.Role
import com.redhat.theses.auth.User
import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class ApplicationController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.ApplicationService
     */
    def applicationService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        [applicationInstanceList: Application.list(params), applicationInstanceTotal: Application.count]
    }

    @Secured(['ROLE_STUDENT'])
    def create(Long id) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }
        if (topicInstance.universities.empty) {
            flash.message = message(code: 'application.impossible.no.university', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        def application = new Application(topic: topicInstance)
        [application: application, universities: topicInstance?.universities]
    }

    @Secured(['ROLE_STUDENT'])
    def save() {
        def application = new Application(params.application)
        User user = springSecurityService.currentUser
        application.applicant = user

        def topicInstance = Topic.get(application.topic.id)

        if (!application.validate() || !application.save()) {
            render(view: "create", model: [application: application, universities: topicInstance?.universities])
            return
        }

        flash.message = message(code: 'application.created', args: [application.id])
        redirect(action: "show", id: topicInstance?.id)
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def approve(Long id) {
        def applicationInstance = Application.get(id)
        if (!applicationInstance) {
            flash.message = message(code: 'application.not.found', args: [id])
            redirect(controller: 'application', action: 'list')
            return
        }

        User user = springSecurityService.currentUser

        if (applicationInstance.topic.owner != user && !applicationInstance.topic.supervisors.contains(user)
            && !SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {

            flash.message = message(code: 'action.permission.denied')
            redirect(controller: 'application', action: 'list')
            return
        }

        if (!applicationService.approve(applicationInstance)) {
            render view: 'show', model: [applicationInstance: applicationInstance]
            return
        }
        flash.message = message(code: 'application.approved')

        redirect(controller: 'application', action: 'show', id: applicationInstance.id)
    }

    def show(Long id) {
        def applicationInstance = Application.get(id)
        if (!applicationInstance) {
            flash.message = message(code: 'application.not.found', args: [id])
            redirect(action: "list")
            return
        }

        [applicationInstance: applicationInstance]
    }
}
