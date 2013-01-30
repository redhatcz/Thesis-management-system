package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

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


        User user = springSecurityService.currentUser
        def universities = user.organizations.findAll {it.id in topicInstance.universities*.id}
        def application = new Application(topic: topicInstance)
        [application: application, universities: universities]
    }

    @Secured(['ROLE_STUDENT'])
    def save() {
        def application = new Application(params.application)
        User user = springSecurityService.currentUser
        application.applicant = user

        def topicInstance = Topic.get(application.topic.id)

        if (!application.validate() || !application.save()) {
            def universities = user.organizations.findAll {it.id in topicInstance?.universities*.id}
            render(view: "create", model: [application: application, universities: universities])
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

        if (applicationInstance.topic.owner != user) {
            flash.message = message(code: 'action.permission.denied')
            redirect(controller: 'application', action: 'list')
            return
        }

        applicationService.approve(applicationInstance)
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
