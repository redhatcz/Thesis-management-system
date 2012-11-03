package com.redhat.theses

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import com.redhat.theses.auth.User

class TopicController {

    def topicService;

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [topicInstanceList: Topic.list(params), topicInstanceTotal: Topic.count()]
    }

    def listUsersFromUniversityByName(String term, Long organizationId) {
        def query = Membership.where {organization.id == organizationId  && user.fullName =~ "%${term}%"}
        def memberships = query.find([max: 5])
        def userList = []

        memberships.each {
            userList << [id: it.id, label: it.user.fullName, name: it.user.fullName]
        }
        render userList as JSON
    }

    def create() {
        def membershipsCommand = new MembershipsCommand();
        bindData(membershipsCommand, params.supervisions)
        [topicInstance: new Topic(params), membershipCommand: membershipsCommand, universities: University.all, owners: User.all]
    }

    def save() {
        def topicInstance = new Topic(params.topic)
        def membershipsCommand = new MembershipsCommand()
        bindData(membershipsCommand, params.supervisions)

        if (!membershipsCommand.validate() || !topicService.saveWithSupervision(topicInstance, membershipsCommand.memberships)) {
            render(view: "create", model: [topicInstance: topicInstance, membershipCommand: membershipsCommand,
                    universities: University.all, owners: User.all])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'topic.label', default: 'Topic'), topicInstance.id])
        redirect(action: "show", id: topicInstance.id)
    }

    def show(Long id) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
            return
        }

        def supervisions = topicInstance.supervisions
                .collect { it.membership }
                .groupBy { it.organization }
                .collectEntries {key, val -> [key, val.user]}

        [topicInstance: topicInstance, supervisions: supervisions]
    }

    def edit(Long id, MembershipsCommand membershipsCommand) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
            return
        }

        membershipsCommand.memberships += topicInstance.supervisions.collect {it.membership}

        [topicInstance: topicInstance, membershipCommand: membershipsCommand,
                universities: University.all, owners: User.all]
    }

    def update() {
        Long id = params.topic.long("id")
        Long version = params.topic.long("version")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (topicInstance.version > version) {
                topicInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'topic.label', default: 'Topic')] as Object[],
                          "Another user has updated this Topic while you were editing")
                render(view: "edit", model: [topicInstance: topicInstance])
                return
            }
        }

        topicInstance.properties = params.topic
        def membershipsCommand = new MembershipsCommand()
        bindData(membershipsCommand, params.supervisions)
        membershipsCommand.memberships = membershipsCommand.memberships.findAll()

        if (!membershipsCommand.validate() || !topicService.saveWithSupervision(topicInstance, membershipsCommand.memberships))  {
            render(view: "edit", model: [topicInstance: topicInstance, membershipCommand: membershipsCommand,
                    universities: University.all, owners: User.all])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'topic.label', default: 'Topic'), topicInstance.id])
        redirect(action: "show", id: topicInstance.id)
    }

    def delete() {
        Long id = params.topic.long("id")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
            return
        }

        if (topicService.deleteWithSupervisions(topicInstance)) {
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "show", id: id)
        }
    }
}
