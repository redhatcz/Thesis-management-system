                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     package com.redhat.theses

import com.redhat.theses.util.Util

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

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        [thesisInstanceList: Thesis.list(params), thesisInstanceTotal: Thesis.count]
    }

    def show(Long id) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def commentsTotal = Comment.countByArticle(thesisInstance)
        def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)

        def comments = Comment.findAllByArticle(thesisInstance,
                [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser, thesisInstance)

        [thesisInstance: thesisInstance, comments: comments, commentsTotal: commentsTotal, subscriber: subscriber]
    }

    def create(Long id) {
        def thesisInstance = new Thesis(params.thesis)
        def disabledTopicField = false
        if (id) {
            thesisInstance.topic = Topic.get(id)
            disabledTopicField = true
        }
        [thesisInstance: thesisInstance, disabledTopicField: disabledTopicField]
    }

    def save() {
        def thesisInstance = new Thesis(params.thesis)
        thesisInstance.status = Thesis.Status.IN_PROGRESS

        if (!thesisService.save(thesisInstance)) {
            render view: 'create', model: [thesisInstance: thesisInstance]
            return
        }

        flash.message = message(code: 'thesis.created', args: [ thesisInstance.id])
        redirect action: 'show', id: thesisInstance.id
    }

    def edit(Long id) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }
        [thesisInstance: thesisInstance, statusList: Thesis.Status.values(), gradeList: Thesis.Grade.values()]
    }

    def update() {
        Long id = params.thesis.long("id")
        Long version = params.thesis.long("version")
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (version && thesisInstance.version > version) {
            thesisInstance.errors.rejectValue("version", "thesis.optimistic.lock.error")
            render view: "edit", model:
                    [thesisInstance: thesisInstance, statusList: Thesis.Status.values(), gradeList: Thesis.Grade.values()]
            return
        }

        thesisInstance.properties = params.thesis
        if (!thesisService.save(thesisInstance)) {
            render view: 'edit', model:
                    [thesisInstance: thesisInstance, statusList: Thesis.Status.values(), gradeList: Thesis.Grade.values()]
            return
        }

        flash.message = message(code: 'thesis.updated', args: [thesisInstance.id])
        redirect action: "show", id: thesisInstance.id
    }

    def delete() {
        Long id = params.thesis.int('id')
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (thesisService.delete(thesisInstance)) {
            flash.message = message(code: 'thesis.updated', args: [id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'thesis.not.updated', args: [id])
            redirect(action: "show", id: id)
        }
    }
}
