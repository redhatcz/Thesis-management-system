package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util

class TopicController {

    def topicService;

    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        def rootTags = Tag.findAllByParentIsNull()
        [topicInstanceList: Topic.list(params), topicInstanceTotal: Topic.count(), tags: rootTags]
    }

    def tag(Long id, Integer max) {
        if (!id){
            redirect(action: "list", permanent: true)
            return
        }

        params.max = Util.max(max)
        def tag = Tag.get(id)

        if (!tag) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tag.label', default: 'Tag'), id])
            redirect(action: "list")
            return
        }

        // TODO: possible refactoring
        def topicInstanceList = (tag.allSubTags + tag).collect {Topic.findAllByTag(it)}.flatten().unique()

        [topicInstanceList: topicInstanceList, topicInstanceTotal: Topic.count(),
                currentTag: tag, tags: tag.subTags]
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

        def commentsTotal = Comment.countByArticle(topicInstance)
        def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)

        def comments = Comment.findAllByArticle(topicInstance,
                [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser, topicInstance)

        [topicInstance: topicInstance, supervisions: supervisions,
                comments: comments, commentsTotal: commentsTotal, subscriber: subscriber]
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
