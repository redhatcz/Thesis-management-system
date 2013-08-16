package com.redhat.theses.auth

import com.redhat.theses.*
import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

class UserController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.auth.UserService
     */
    def userService

    /**
     * Dependency injection of com.redhat.theses.FilterService
     */
    def filterService

    static final Integer MAX_THESES_AND_TOPICS = 5

    static final Integer MAX_USERS = 20

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static defaultAction = "list"

    def list(Integer max) {
        params.max = Util.max(max, MAX_USERS)

        if (!params.filtering || params.filter?.onlyEnabled) {
            if (!params.filter) {
                params.filter = [:]
            }
            params.filter += [
                    enabled: true,
                    accountExpired: false,
                    accountLocked: false,
                    onlyEnabled: true
            ]
        }

        def userInstanceList = filterService.filter(params, User)
        def userInstanceTotal = filterService.count(params, User)

        [userInstanceList: userInstanceList, userInstanceTotal: userInstanceTotal]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        def userInstance = new User(params.user)
        userInstance.roles = [Role.STUDENT]
        [userInstance: userInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
        def userInstance = new User(params.user)
        // when only one role selected, it is not considered a list by default
        userInstance.roles = params.user?.list('roles')?.collect {Role.valueOf(it)}
        if (!userService.save(userInstance)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'user.created', args: [userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    // TODO: show "sub-actions" should be more dry
    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def ownedTopics = []
        if (userInstance.roles?.contains(Role.OWNER)) {
            ownedTopics = Topic.findAllByOwner(userInstance,
                    [sort:'dateCreated', order:'desc', max: MAX_THESES_AND_TOPICS])
        }

        def supervisedTopics = []
        if (userInstance.roles?.contains(Role.SUPERVISOR)) {
            supervisedTopics = Supervision.findAllBySupervisor(userInstance,
                    [sort:'topic.dateCreated', order:'desc'])*.topic.unique().take(MAX_THESES_AND_TOPICS)
        }

        def supervisedTheses = []
        if (userInstance.roles?.contains(Role.SUPERVISOR)) {
            supervisedTheses = Thesis.findAllBySupervisor(userInstance,
                    [sort:'dateCreated', order:'desc', max: MAX_THESES_AND_TOPICS])
        }

        def assignedTheses =
            Thesis.findAllByAssignee(userInstance, [sort:'dateCreated', order:'desc', max: MAX_THESES_AND_TOPICS])

        [userInstance: userInstance,
                assignedTheses: assignedTheses,
                ownedTopics: ownedTopics,
                supervisedTheses: supervisedTheses,
                supervisedTopics: supervisedTopics]
    }

    def activity(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        params.sort = 'dateCreated'
        params.order= 'desc'
        params.max = Util.DEFAULT_MAX
        def feedList = Feed.findAllByUser(userInstance, params)
        def feedListTotal = Feed.countByUser(userInstance)

        [userInstance: userInstance, feedList: feedList, feedListTotal: feedListTotal]
    }

    def applications(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        params.sort = 'dateCreated'
        params.order= 'desc'
        params.max = Util.DEFAULT_MAX
        def applicationList = Application.findAllByApplicant(userInstance, params)
        def applicationCount = Application.countByApplicant(userInstance)

        [userInstance: userInstance, applicationList: applicationList, applicationCount: applicationCount]
    }

    def supervisions(Long id, Integer max) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def currentUser = springSecurityService.getCurrentUser() == userInstance
        params.max = Util.max(max)
        [topicInstanceList: Topic.findAllBySupervisor(userInstance, params), topicInstanceTotal: Topic.count(),
                userInstance: userInstance, isCurrentUser: currentUser]
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
        Long id = params.user.long("id")
        Long version = params.user.long("version")
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (version != null && userInstance.version > version) {
            userInstance.errors.rejectValue("version", "user.optimistic.lock.error")
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        userInstance.properties = params.user
        // when only one role selected, it is not considered a list by default
        userInstance.roles = params.user?.list('roles')?.collect {Role.valueOf(it)}

        if (!userService.save(userInstance)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'user.updated', args: [userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    @Secured(['ROLE_ADMIN'])
    def delete() {
        Long id = params.user.long("id")
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'user.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (!userService.delete(userInstance)) {
            flash.message = message(code: 'user.not.deleted', args: [id])
            redirect(action: "show", id: id)
            return
        }

        flash.message = message(code: 'user.deleted', args: [id])
        redirect(action: "list")
    }
}
