package com.redhat.theses

import com.redhat.theses.util.Util

class TopicController {

    static final Long TAG_MAX = 10

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

    /**
     * Dependency injection of com.redhat.theses.TagService
     */
    def tagService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Long categoryId, String tagTitle) {
        params.max = Util.max(params.max)

        def category = null
        if (categoryId) {
            category = Category.get(categoryId)

            if (!category) {
                flash.message = message(code: 'category.not.found', args: [categoryId])
                redirect(action: "list")
                return
            }
        }
        def tag = null
        if (tagTitle) {
            tag = Tag.get(new Tag(title: tagTitle))

            if (!tag) {
                flash.message = message(code: 'tag.not.found', args: [tagTitle])
                redirect(action: "list")
                return
            }
        }

        def topics
        def topicCount
        if (category && tag) {
            topics = Topic.findAllByCategoryAndTag(category, tag, params)
            topicCount = Topic.countByCategoryAndTag(category, tag)
        } else if (category && !tag) {
            topics = Topic.findAllByCategory(category, params)
            topicCount = Topic.countByCategory(category)
        } else if (tag && !category) {
            topics = Topic.findAllByTag(tag, params)
            topicCount = Topic.countByTag(tag)
        } else {
            topics = Topic.list(params)
            topicCount = Topic.count()
        }

        def categoryList = Category.findAll()
        def tagListWithUsage = tagService.findAllWithCountUsage([max: TAG_MAX])
        def commentCounts = commentService.countByArticles(topics)

        [topicInstanceList: topics, topicInstanceTotal: topicCount, commentCounts: commentCounts,
                categoryList: categoryList, currentCategory: category, currentTag: tag,
                tagListWithUsage: tagListWithUsage]
    }

    def create(Long categoryId) {
        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)

        def topicInstance = new Topic(params.topic)
        // if category id is set, set default category to the current category
        if (categoryId) {
            def categoryInstance = Category.get(categoryId)
            if (categoryInstance) {
                topicInstance.categories = [categoryInstance]
            }
        }

        // set default types to Bachelor and master
        topicInstance.types = [Type.BACHELOR, Type.MASTER]

        [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                universities: University.all, types: Type.values()]
    }

    def save() {
        def topicInstance = new Topic(params.topic)
        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)

        // setup supervisions
        supervisionCommand.supervisions.each {
            it.topic = topicInstance
        }

        // setup tags
        topicInstance.tags = params.topic.tags.list('title').collect { new Tag(title: it) }.unique{[it.title]}

        if (!topicService.saveWithSupervisions(topicInstance, supervisionCommand.supervisions)) {
            render(view: "create", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, types: Type.values()])
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

        def thesisList = Thesis.findAllByTopic(topicInstance)

        [topicInstance: topicInstance, supervisions: supervisions, thesisList: thesisList,
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
                universities: University.all, types: Type.values()]
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

        // when no types selected, set them to empty list
        if (!params.topic.types) {
            topicInstance.types = []
        }

        // when no categories selected, set them to empty list
        if (!params.topic.categories) {
            topicInstance.categories = []
        }

        // setup tags
        topicInstance.tags = params.topic.tags.list('title').collect { new Tag(title: it) }.unique{[it.title]}

        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)
        supervisionCommand.supervisions = supervisionCommand.supervisions.findAll()

        if (version != null && topicInstance.version > version) {
            topicInstance.errors.rejectValue("version", "topic.optimistic.lock.error")
            render(view: "edit", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, types: Type.values()])
            return
        }

        def previousSupervisions = Supervision.findAllByTopic(topicInstance)
        def withExistingSupervisions = supervisionCommand.supervisions.collect { s ->
            def supervision = previousSupervisions.find { it.supervisor == s.supervisor && it.university == s.university }
            if (!supervision){
                supervision = s
                supervision.topic = topicInstance
            }
            supervision
        }.unique{[it.supervisor, it.university]}
        if (!topicService.saveWithSupervisions(topicInstance, withExistingSupervisions))  {
            render(view: "edit", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, types: Type.values()])
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
