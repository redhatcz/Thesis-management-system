package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util

class TopicController {

    /**
     * Dependency injection of com.redhat.theses.TopicService
     */
    def topicService;

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.CommentService
     */
    def commentService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        def rootTags = Tag.findAllByParentIsNull()
        def topics = Topic.list(params)
        def commentCounts = commentService.countByArticles(topics)

        [topicInstanceList: topics, topicInstanceTotal: Topic.count(),
                commentCounts: commentCounts, tags: rootTags]
    }

    def tag(Long id, Integer max) {
        if (!id){
            redirect(action: "list", permanent: true)
            return
        }

        params.max = Util.max(max)
        def tag = Tag.get(id)

        if (!tag) {
            flash.message = message(code: 'tag.not.found', args: [id])
            redirect(action: "list")
            return
        }

        // TODO: possible refactoring
        def topicInstanceList = (tag.allSubTags + tag).collect {Topic.findAllByTag(it)}.flatten().unique()
        def commentCounts = commentService.countByArticles(topicInstanceList)

        [topicInstanceList: topicInstanceList, topicInstanceTotal: Topic.count(),
                currentTag: tag, tags: tag.subTags, commentCounts: commentCounts]
    }

    def create() {
        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)
        [topicInstance: new Topic(params.topic), supervisionCommand: supervisionCommand,
                universities: University.all, owners: User.all]
    }

    def save() {
        def topicInstance = new Topic(params.topic)
        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)

        if (!topicService.saveWithSupervisions(topicInstance, supervisionCommand.supervisions)) {
            render(view: "create", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, owners: User.all])
            return
        }

        flash.message = message(code: 'topic.created', args: [topicInstance.id])
        redirect(action: "show", id: topicInstance.id, params: [headline: Util.hyphenize(topicInstance.title)])
    }

    def show(Long id, String headline) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (Util.hyphenize(topicInstance.title) != headline) {
            redirect(action: 'show', id: id, params: [headline: Util.hyphenize(topicInstance.title)], permanent: true)
        }

        def supervisions = topicInstance.supervisions

        def commentsTotal = Comment.countByArticle(topicInstance)
        def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)

        def comments = Comment.findAllByArticle(topicInstance,
                [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser, topicInstance)

        [topicInstance: topicInstance, supervisions: supervisions,
                comments: comments, commentsTotal: commentsTotal, subscriber: subscriber]
    }

    def edit(Long id, SupervisionCommand supervisionCommand) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        supervisionCommand.supervisions += topicInstance.supervisions

        [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                universities: University.all, owners: User.all]
    }

    def update() {
        Long id = params.topic.long("id")
        Long version = params.topic.long("version")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        topicInstance.properties = params.topic
        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)
        supervisionCommand.supervisions = supervisionCommand.supervisions.findAll()

        if (version != null && topicInstance.version > version) {
            topicInstance.errors.rejectValue("version", "topic.optimistic.lock.error")
            render(view: "edit", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, owners: User.all])
            return
        }

        //TODO: refactoring?
        def withExistingSupervisions = supervisionCommand.supervisions.collect {
            def supervision = Supervision.findByTopicAndSupervisorAndUniversity(topicInstance, it.supervisor, it.university)
            if (supervision) {
                supervision
            } else {
                it
            }
        }
        if (!topicService.saveWithSupervisions(topicInstance, withExistingSupervisions))  {
            render(view: "edit", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, owners: User.all])
            return
        }

        flash.message = message(code: 'topic.updated', args: [topicInstance.id])
        redirect(action: "show", id: topicInstance.id, params: [headline: Util.hyphenize(topicInstance.title)])
    }

    def delete() {
        Long id = params.topic.long("id")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (topicService.deleteWithSupervisions(topicInstance)) {
            flash.message = message(code: 'topic.deleted', args: [id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'topic.not.deleted', args: [id])
            redirect(action: "show", id: id, params: [headline: Util.hyphenize(topicInstance.title)])
        }
    }
}
