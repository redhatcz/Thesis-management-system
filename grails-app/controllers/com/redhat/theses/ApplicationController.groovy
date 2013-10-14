package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.events.ApplicationEvent
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

    /**
     * Dependency injection of com.redhat.theses.FilterService
     */
    def filterService

    static allowedMethods = [save: "POST", approveSave: "POST"]

    static defaultAction = "list"

    def list(Integer max) {
        params.max = Util.max(max)

        if (!params.filter) {
            params.filter = [:]
        }
        if (!params?.filtering) {
            params.filter << [status: AppStatus.PENDING]
        }

        def applicationInstanceList = filterService.filter(params, Application)
        def applicationInstanceTotal = filterService.count(params, Application)

        [applicationInstanceList: applicationInstanceList, applicationInstanceTotal: applicationInstanceTotal]
    }

    @Secured(['ROLE_STUDENT'])
    def create(Long id) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        if (!topicInstance.enabled) {
            flash.message = message(code: 'application.topic.disabled')
            redirect(controller: 'topic', action: 'show', id: topicInstance.id)
            return
        }

        if (topicInstance.universities.empty) {
            flash.message = message(code: 'application.impossible.no.university', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        def application = new Application(topic: topicInstance)
        [applicationInstance: application, universities: topicInstance?.universities]
    }

    @Secured(['ROLE_STUDENT'])
    def save() {
        def application = new Application(params.application)
        application.status = AppStatus.PENDING
        User user = springSecurityService.currentUser
        application.applicant = user

        if (!application.validate() || !application.save()) {
            def topicInstance = Topic.get(application.topic.id)
            render(view: "create", model: [applicationInstance: application, universities: topicInstance?.universities])
            return
        }

        event("applicationCreated", new ApplicationEvent(application, user))
        flash.message = message(code: 'application.created', args: [application.id])
        redirect(action: "show", id: application?.id)
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def approve(Long id) {
        def applicationInstance = Application.get(id)
        if (!applicationInstance) {
            flash.message = message(code: 'application.not.found', args: [id])
            redirect(action: 'list')
            return
        }

        if (applicationInstance.status == AppStatus.APPROVED) {
            flash.message = message(code: 'application.already.approved')
            redirect(action: 'show', id: applicationInstance.id)
        }

        User user = springSecurityService.currentUser

        if (applicationInstance.topic.owner != user && !applicationInstance.topic.supervisors.contains(user)
            && !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {

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

        // Set university
        thesisInstance.university = applicationInstance.university

        // Set type
        thesisInstance.type = applicationInstance.type

        [thesisInstance: thesisInstance, applicationInstance: applicationInstance, disabledAssigneeField: true,
                disabledTopicField: true, universityList: University.all, typeList: Type.values()]
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
                    disabledAssigneeField: true, disabledTopicField: true, universityList: University.all,
                    typeList: Type.values()]
            return
        }

        event("applicationApproved", new ApplicationEvent(applicationInstance, user))
        flash.message = message(code: 'application.approved')
        redirect(controller: 'thesis', action: 'show', id: thesisInstance.id,
                params: [headline: Util.hyphenize(thesisInstance.title)])
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def decline(Long id) {
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

        applicationInstance.status = AppStatus.DECLINED

        if (!applicationInstance.save()) {
            render view: 'approve', model: [applicationInstance: applicationInstance]
            return
        }

        event("applicationDeclined", new ApplicationEvent(applicationInstance, user))
        flash.message = message(code: 'application.declined')
        redirect(action: 'list')
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
