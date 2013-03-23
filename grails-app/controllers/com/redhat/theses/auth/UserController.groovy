package com.redhat.theses.auth

import com.redhat.theses.Application
import com.redhat.theses.Feed
import com.redhat.theses.Thesis
import com.redhat.theses.Topic
import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

class UserController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injeciton of com.redhat.theses.auth.UserService
     */
    def userService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static defaultAction = "list"

    def list(Integer max) {
        params.max = Util.max(max)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
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

        def thesisInstanceList =
            Thesis.findAllByAssignee(userInstance, [sort:'dateCreated', order:'desc', max: 5])

        [userInstance: userInstance, thesisInstanceList: thesisInstanceList]
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
            flash.message = message(code: 'user.deleted', args: [id])
            redirect(action: "show", id: id)
            return
        }

        flash.message = message(code: 'user.not.deleted', args: [id])
        redirect(action: "list")
    }
}
