package com.redhat.theses

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

    static allowedMethods = [save: "POST", approveSave: "POST"]

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
        redirect(action: "show", id: application?.id)
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def approve(Long id) {
        def applicationInstance = Application.get(id)
        println applicationInstance
        if (!applicationInstance) {
            flash.message = message(code: 'application.not.found', args: [id])
            redirect(action: 'list')
            return
        }

        if (applicationInstance.approved) {
            flash.message = message(code: 'application.already.approved')
            redirect(action: 'show', id: applicationInstance.id)
        }

        User user = springSecurityService.currentUser

        if (applicationInstance.topic.owner != user && !applicationInstance.topic.supervisors.contains(user)
            && !SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {

            flash.message = message(code: 'security.denied.message')
            redirect(action: 'list')
            return
        }

        def thesisInstance = new Thesis(params.thesis)

        // Set topic
        thesisInstance.topic = applicationInstance.topic

        // Set default title to topic title
        thesisInstance.title = applicationInstance.topic.title

        // Set assignee
        thesisInstance.assignee = applicationInstance.applicant

        [thesisInstance: thesisInstance, applicationInstance: applicationInstance,
                disabledAssigneeField: true, disabledTopicField: true]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def approveSave(Long id) {
        def applicationInstance = Application.get(id)
        if (!applicationInstance) {
            flash.message = message(code: 'application.not.found', args: [id])
            redirect(action: 'list')
            return
        }

        User user = springSecurityService.currentUser

        if (applicationInstance.topic.owner != user && !applicationInstance.topic.supervisors.contains(user)
                && !SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {

            flash.message = message(code: 'security.denied.message')
            redirect(action: 'list')
            return
        }

        def thesisInstance = new Thesis(params.thesis)

        thesisInstance.status = Status.IN_PROGRESS

        if (!applicationService.approve(applicationInstance, thesisInstance)) {
            render view: 'approve', model: [thesisInstance: thesisInstance, applicationInstance: applicationInstance,
                    disabledAssigneeField: true, disabledTopicField: true]
            return
        }

        flash.message = message(code: 'application.approved')

        redirect(controller: 'thesis', action: 'show', id: thesisInstance.id)
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
