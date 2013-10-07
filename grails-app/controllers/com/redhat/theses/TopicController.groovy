package com.redhat.theses

import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

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

    /**
     * Dependency injection of com.redhat.theses.FilterService
     */
    def filterService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static defaultAction = "list"

    def list() {
        params.max = Util.max(params.max)

        // Check onlyEnabled by default
        if (!params?.filtering || params.filter?.onlyEnabled) {
            if (!params.filter) {
                params.filter = [:]
            }
            params.filter += [
                    enabled: true,
                    onlyEnabled: true
            ]
        }

        def category = null
        if (params.filter?.categories?.id) {
            category = Category.get(params.filter?.categories?.long('id'))
        }

        def tag = null
        if (params.filter?.tags?.title) {
            tag = Tag.get(new Tag(title: params.filter?.tags?.title))
        }

        def topics = filterService.filter(params, Topic)
        def topicCount = filterService.count(params, Topic)

        def categoryList = Category.findAll()
        def tagListWithUsage = tagService.findAllWithCountUsage(Topic, [max: TAG_MAX])

        def publicCommentsOnly = !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER, ROLE_SUPERVISOR')
        def commentCounts = commentService.countByArticles(topics, publicCommentsOnly)

        [topicInstanceList: topics, topicInstanceTotal: topicCount, commentCounts: commentCounts,
                categoryList: categoryList, currentCategory: category, currentTag: tag,
                tagListWithUsage: tagListWithUsage, universities: University.all]
    }

    def printableList() {
        // Check onlyEnabled by default
        if (!params?.filtering || params.filter?.onlyEnabled) {
            if (!params.filter) {
                params.filter = [:]
            }
            params.filter += [
                    enabled: true,
                    onlyEnabled: true
            ]
        }
        // Check title, owner, categories and types checkboxes by default
        if (!params?.viewing) {
            if (!params.view) {
                params.view = [:]
            }
            params.view += [
                    title: true,
                    owner: true,
                    categories: true,
                    types: true
            ]
        }
        // Check supervisors checkbox by default
        if (!params?.viewingSupervisors) {
            if (!params.view) {
                params.view = [:]
            }
            params.view += [
                    supervisors: true
            ]
        }

        def topics = filterService.filter(params, Topic)
        def topicsWithSupervisors = [:]
        def universityId = params.filter?.universities?.long('id')
        def university = null
        def isUniversityFilled = universityId != null
        if (topics) {
            if (universityId) {
                university = University.get(universityId)
                topicsWithSupervisors = topicService
                        .findAllWithSupervisorsByTopicsAndUniversity(topics, university)
            }

            if (params?.option?.noSupervisors) {
                topicsWithSupervisors = topicsWithSupervisors.findAll { it.value == [] }
                topics = topicsWithSupervisors.collect { it.key }
            }
        }

        [topics: topics, topicsWithSupervisors: topicsWithSupervisors, universityView: isUniversityFilled,
                university: university, universities: University.all]
    }

    @Secured(['ROLE_OWNER'])
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
        topicInstance.types = [Type.BACHELOR, Type.DIPLOMA]

        [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                universities: University.all, types: Type.values()]
    }

    @Secured(['ROLE_OWNER'])
    def save() {
        def topicInstance = new Topic(params.topic)
        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)

        // setup supervisions
        supervisionCommand.supervisions.each {
            it.topic = topicInstance
        }

        // setup tags
        topicInstance.tags = params.topic?.tags?.list('title')?.collect { new Tag(title: it) }?.unique{[it.title]}

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

        def commentsTotal
        def comments
        if (springSecurityService.isLoggedIn() && SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER, ROLE_SUPERVISOR')) {
            commentsTotal = Comment.countByArticle(topicInstance)
            def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)
            comments = Comment.findAllByArticle(topicInstance,
                    [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])
        } else {
            commentsTotal = Comment.countByArticleAndPrivateComment(topicInstance, false)
            def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)
            comments = Comment.findAllByArticleAndPrivateComment(topicInstance, false,
                    [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])
        }

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser, topicInstance)

        def thesisList = Thesis.findAllByTopic(topicInstance)

        [topicInstance: topicInstance, supervisions: supervisions, thesisList: thesisList,
                comments: comments, commentsTotal: commentsTotal, subscriber: subscriber]
    }

    @Secured(['ROLE_OWNER'])
    def edit(Long id, SupervisionCommand supervisionCommand) {
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (topicInstance.owner != springSecurityService.currentUser &&
                !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {
            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: "show", id: topicInstance.id, params: [headline: Util.hyphenize(topicInstance.title)])
            return
        }

        supervisionCommand.supervisions += topicInstance.supervisions

        [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                universities: University.all, types: Type.values()]
    }

    @Secured(['ROLE_OWNER'])
    def update() {
        Long id = params.topic.long("id")
        Long version = params.topic.long("version")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (topicInstance.owner != springSecurityService.currentUser&&
                !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {
            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: "show", id: topicInstance.id, params: [headline: Util.hyphenize(topicInstance.title)])
            return
        }

        topicInstance.properties = params.topic

        // when no types selected, set them to empty list
        if (!params.topic.types) {
            topicInstance.types = []
        }

        // when no types selected, set them to empty list
        if (!params.topic.universities) {
            topicInstance.universities = []
        }


        // when no categories selected, set them to empty list
        if (!params.topic.categories) {
            topicInstance.categories = []
        }

        // setup tags
        topicInstance.tags = params.topic?.tags?.list('title')?.collect { new Tag(title: it) }?.unique{[it.title]}

        def supervisionCommand = new SupervisionCommand()
        bindData(supervisionCommand, params.supervisionCommand)
        supervisionCommand.supervisions = supervisionCommand.supervisions.findAll()
        supervisionCommand.validate()

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
        if (supervisionCommand.hasErrors() || !topicService.saveWithSupervisions(topicInstance, withExistingSupervisions))  {
            render(view: "edit", model: [topicInstance: topicInstance, supervisionCommand: supervisionCommand,
                    universities: University.all, types: Type.values()])
            return
        }

        flash.message = message(code: 'topic.updated', args: [topicInstance.id])
        redirect(action: "show", id: topicInstance.id, params: [headline: Util.hyphenize(topicInstance.title)])
    }

    @Secured(['ROLE_OWNER'])
    def delete() {
        Long id = params.topic.long("id")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (topicInstance.owner != springSecurityService.currentUser &&
                !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {
            flash.message = message(code: 'security.denied.action.message', args: [id])
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
