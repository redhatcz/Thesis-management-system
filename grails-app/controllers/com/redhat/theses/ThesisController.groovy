package com.redhat.theses
import com.redhat.theses.util.Util
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class ThesisController {

    static final Long TAG_MAX = 10

    static allowedMethods = [save: 'POST', update: 'POST']

    /**
     * Dependency injection of com.redhat.theses.ThesisService
     */
    def thesisService

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.grails.mongodb.GridFileService
     */
    def gridFileService

    /**
     * Dependency injection of com.redhat.theses.TagService
     */
    def tagService

    /**
     * Dependency injection of com.redhat.theses.FilterService
     */
    def filterService

    /**
     * Dependency injection of com.redhat.theses.CommentService
     */
    def commentService

    static defaultAction = "list"

    def list() {
        params.max = Util.max(params.max)

        def tag = null
        if (params.filter?.tags?.title) {
            tag = Tag.get(new Tag(title: params.filter?.tags?.title))
        }

        def tagListWithUsage = tagService.findAllWithCountUsage(Thesis, [max: TAG_MAX])

        def theses = filterService.filter(params, Thesis)
        def thesesCount = filterService.count(params, Thesis)

        def publicCommentsOnly = !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER, ROLE_SUPERVISOR')
        def commentCounts = commentService.countByArticles(theses, publicCommentsOnly)

        [thesisInstanceList: theses, thesisInstanceTotal: thesesCount, universityList: University.all,
                currentTag: tag, tagListWithUsage: tagListWithUsage, commentCounts: commentCounts]
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

        def commentsTotal
        def comments
        if (springSecurityService.isLoggedIn() && SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER, ROLE_SUPERVISOR')) {
            commentsTotal = Comment.countByArticle(thesisInstance)
            def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)
            comments = Comment.findAllByArticle(thesisInstance,
                    [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])
        } else {
            commentsTotal = Comment.countByArticleAndPrivateComment(thesisInstance, false)
            def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)
            comments = Comment.findAllByArticleAndPrivateComment(thesisInstance, false,
                    [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])
        }

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser, thesisInstance)
        def files = gridFileService.getAllFiles(thesisInstance).sort {it.uploadDate}


        def isThesisAdmin = SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') ||
                springSecurityService.currentUser == thesisInstance.supervisor ||
                springSecurityService.currentUser == thesisInstance.topic.owner

        def isAuthorized = isThesisAdmin ||
                springSecurityService.currentUser == thesisInstance.assignee

        [thesisInstance: thesisInstance, comments: comments, isAuthorized: isAuthorized,
         isThesisAdmin: isThesisAdmin, commentsTotal: commentsTotal, subscriber: subscriber,
         files: files]
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
                thesisInstance.tags = topicInstance.tags
                disabledTopicField = true
            }
        }

        // Set default title to topic title
        if (thesisInstance.topic && !thesisInstance.title) {
            thesisInstance.title = thesisInstance.topic.title
        }

        [thesisInstance: thesisInstance, disabledTopicField: disabledTopicField,
                universityList: University.all, typeList: Type.values()]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def save(Long id) {
        def thesisInstance = new Thesis(params.thesis)

        thesisInstance.status = Status.IN_PROGRESS

        thesisInstance.tags = params.thesis?.tags?.list('title')?.collect { new Tag(title: it) }?.unique{[it.title]}

        if (!thesisService.save(thesisInstance)) {
            render view: 'create', model: [thesisInstance: thesisInstance, disabledTopicField: id && true,
                    universityList: University.all, typeList: Type.values()]
            return
        }

        flash.message = message(code: 'thesis.created', args: [ thesisInstance.id])
        redirect action: 'show', id: thesisInstance.id, params: [headline: Util.hyphenize(thesisInstance.title)]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER', 'ROLE_STUDENT'])
    def edit(Long id) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }


        def isAuthorized = SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') ||
                springSecurityService.currentUser == thesisInstance.assignee ||
                springSecurityService.currentUser == thesisInstance.supervisor ||
                springSecurityService.currentUser == thesisInstance.topic.owner


        if (!isAuthorized) {
            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: 'show', id: id, params: [headline: Util.hyphenize(thesisInstance.title)])
            return
        }

        def supervisors = Supervision.findAllByTopic(thesisInstance.topic)*.supervisor

        [thesisInstance: thesisInstance, statusList: Status.values(), gradeList: Grade.values(),
         supervisors: supervisors, universityList: University.all, typeList: Type.values()]
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER', 'ROLE_STUDENT'])
    def update() {
        Long id = params.thesis.long("id")
        Long version = params.thesis.long("version")
        def user = springSecurityService.currentUser

        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def isThesisAdmin = SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') ||
                springSecurityService.currentUser == thesisInstance.supervisor ||
                springSecurityService.currentUser == thesisInstance.topic.owner

        def isAuthorized = isThesisAdmin ||
                springSecurityService.currentUser == thesisInstance.assignee

        if (!isAuthorized) {
            flash.message = message(code: 'security.denied.action.message', args: [id])
            redirect(action: 'show', id: id, params: [headline: Util.hyphenize(thesisInstance.title)])
            return
        }

        if (version && thesisInstance.version > version) {
            thesisInstance.errors.rejectValue("version", "thesis.optimistic.lock.error")
            render view: "edit", model:
                    [thesisInstance: thesisInstance, statusList: Status.values(), gradeList: Grade.values()]
            return
        }

        // setup tags
        thesisInstance.tags = params.thesis?.tags?.list('title')?.collect { new Tag(title: it) }?.unique{[it.title]}

        def isAssignee = thesisInstance.assignee == user
        if (isThesisAdmin) {
            thesisInstance.supervisor = null;
            bindData(thesisInstance, params.thesis)
        } else {
            def include = ['tags.title', 'thesisAbstract']
            bindData(thesisInstance, params, [include: include])
        }

        if (!thesisService.save(thesisInstance, !isAssignee)) {
            render view: 'edit', model:
                    [thesisInstance: thesisInstance, statusList: Status.values(), gradeList: Grade.values()]
            return
        }

        flash.message = message(code: 'thesis.updated', args: [thesisInstance.title])
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
                thesisInstance.topic && thesisInstance.topic.owner != springSecurityService.currentUser &&
                !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')) {
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

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def notes(Long id) {
        def thesisInstance = Thesis.get(id)
        def result = [success: false, message: null, content: null]

        if (!thesisInstance) {
            result.message = message(code: 'thesis.not.found', args: [id])
            return render(text: result as JSON, contentType: 'text/json')
        }

        def isAuthorized = SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') ||
                springSecurityService.currentUser == thesisInstance.supervisor ||
                springSecurityService.currentUser == thesisInstance.topic.owner

        if (!isAuthorized) {
            result.message= message(code: 'security.denied.action.message', args: [id])
            return render(text: result as JSON, contentType: 'text/json')
        }
        result.success = true
        result.content = thesisInstance.notes

        return render(text: result as JSON, contentType: 'text/json')
    }

    @Secured(['ROLE_SUPERVISOR', 'ROLE_OWNER'])
    def updateNotes() {
        Long id = params.thesis.long("id")
        def user = springSecurityService.currentUser

        def result = [success: false, message: null, content: null]

        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            result.message = message(code: 'thesis.not.found', args: [id])
            return render(text: result as JSON, contentType: 'text/json')
        }

        def isAuthorized = SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') ||
                springSecurityService.currentUser == thesisInstance.supervisor ||
                springSecurityService.currentUser == thesisInstance.topic.owner

        if (!isAuthorized) {
            result.message= message(code: 'security.denied.action.message', args: [id])
            return render(text: result as JSON, contentType: 'text/json')
        }

        def allowed = ['notes']
        def updated = params.thesis.findAll {key, val -> key in allowed}

        thesisInstance.properties = updated

        if (!thesisService.save(thesisInstance, false)) {
            return render(text: result as JSON, contentType: 'text/json')
        }

        result.success = true
        result.content = thesisInstance.notes
        return render(text: result as JSON, contentType: 'text/json')
    }
}
