package com.redhat.theses

import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

class ThesisController {

    static allowedMethods = [save: 'POST', update: 'POST']

    /**
     * Dependency injection of com.redhat.theses.ThesisService
     */
    def thesisService

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    static defaultAction = "list"

    def list(Integer max) {
        params.max = Util.max(max)
        [thesisInstanceList: Thesis.list(params), thesisInstanceTotal: Thesis.count]
    }

    def show(Long id, String headline) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (Util.hyphenize(thesisInstance.title) != headline) {
            redirect(action: 'show', id: id, params: [headline: Util.hyphenize(thesisInstance.title)], permanent: true)
        }

        def commentsTotal = Comment.countByArticle(thesisInstance)
        def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)

        def comments = Comment.findAllByArticle(thesisInstance,
                [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser, thesisInstance)

        [thesisInstance: thesisInstance, comments: comments, commentsTotal: commentsTotal, subscriber: subscriber]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def create(Long id) {
        def thesisInstance = new Thesis(params.thesis)

        // If creating from topic
        def disabledTopicField = false
        if (id) {
            def topicInstance = Topic.get(id)

            if (topicInstance) {
                thesisInstance.topic = topicInstance
                disabledTopicField = true
            }
        }

        // Set default title to topic title
        if (thesisInstance.topic && !thesisInstance.title) {
            thesisInstance.title = thesisInstance.topic.title
        }

        [thesisInstance: thesisInstance, disabledTopicField: disabledTopicField]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def save() {
        def thesisInstance = new Thesis(params.thesis)

        thesisInstance.status = Status.IN_PROGRESS

        if (!thesisService.save(thesisInstance)) {
            render view: 'create', model: [thesisInstance: thesisInstance]
            return
        }

        flash.message = message(code: 'thesis.created', args: [ thesisInstance.id])
        redirect action: 'show', id: thesisInstance.id, params: [headline: Util.hyphenize(thesisInstance.title)]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def edit(Long id) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (thesisInstance.supervisor != springSecurityService.currentUser &&
                thesisInstance.topic && thesisInstance.topic.owner != springSecurityService.currentUser) {

            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: "list")
            return
        }

        def supervisors = Supervision.findAllByTopic(thesisInstance.topic)*.supervisor

        [thesisInstance: thesisInstance, statusList: Status.values(), gradeList: Grade.values(),
         supervisors: supervisors]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def update() {
        Long id = params.thesis.long("id")
        Long version = params.thesis.long("version")
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (thesisInstance.supervisor != springSecurityService.currentUser &&
                thesisInstance.topic && thesisInstance.topic.owner != springSecurityService.currentUser) {

            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: "list")
            return
        }

        if (version && thesisInstance.version > version) {
            thesisInstance.errors.rejectValue("version", "thesis.optimistic.lock.error")
            render view: "edit", model:
                    [thesisInstance: thesisInstance, statusList: Status.values(), gradeList: Grade.values()]
            return
        }

        thesisInstance.properties = params.thesis
        if (!thesisService.save(thesisInstance)) {
            render view: 'edit', model:
                    [thesisInstance: thesisInstance, statusList: Status.values(), gradeList: Grade.values()]
            return
        }

        flash.message = message(code: 'thesis.updated', args: [thesisInstance.id])
        redirect action: "show", id: thesisInstance.id, params: [headline: Util.hyphenize(thesisInstance.title)]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def delete() {
        Long id = params.thesis.int('id')
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (thesisInstance.supervisor != springSecurityService.currentUser &&
                thesisInstance.topic && thesisInstance.topic.owner != springSecurityService.currentUser) {
            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: "list")
            return
        }

        if (thesisService.delete(thesisInstance)) {
            flash.message = message(code: 'thesis.deleted', args: [id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'thesis.not.deleted', args: [id])
            redirect(action: "show", id: id, params: [headline: Util.hyphenize(thesisInstance.title)])
        }
    }
}
