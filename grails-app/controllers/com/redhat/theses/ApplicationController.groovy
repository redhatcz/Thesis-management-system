package com.redhat.theses

import com.redhat.theses.auth.User
import grails.plugins.springsecurity.Secured

class ApplicationController {

    def topicService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    @Secured(['ROLE_STUDENT'])
    def create(Long id) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(controller: 'topic', action: "list")
            return
        }
        if (topicInstance.universities.empty) {
            flash.message = message(code: 'application.impossible', args: [message(code: 'application.label', default: 'Application'), id])
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

        flash.message = message(code: 'default.created.message', args: [message(code: 'application.label', default: 'Application'), application.id])
        redirect(controller: 'topic', action: "show", id: topicInstance?.id)
    }
}
