package com.redhat.theses.auth

import org.springframework.dao.DataIntegrityViolationException
import com.redhat.theses.util.Util
import com.redhat.theses.Topic
import com.redhat.theses.MembershipsCommand
import com.redhat.theses.Organization
import com.redhat.theses.Membership

class UserController {

    def springSecurityService

    def userService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        [userInstance: new User(params.user), membershipsCommand: new MembershipsCommand(), organizations: Organization.findAll()]
    }

    def save() {
        def membershipsCommand = new MembershipsCommand()
        bindData(membershipsCommand, params.membershipsCommand)
        def userInstance = new User(params.user)
        if (!userService.saveWithOrganizations(userInstance, membershipsCommand.memberships*.organization)) {
            render(view: "create", model: [userInstance: userInstance,
                    membershipsCommand: membershipsCommand,
                    organizations: Organization.findAll()])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    // TODO: show "sub-actions" should be more dry
    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance, memberships: Membership.findAllByUser(userInstance)]
    }

    def supervisions(Long id, Integer max) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        def currentUser = springSecurityService.getCurrentUser() == userInstance
        params.max = Util.max(max)
        [topicInstanceList: Topic.findAllBySupervisor(userInstance, params), topicInstanceTotal: Topic.count(),
                userInstance: userInstance, isCurrentUser: currentUser]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        def membershipsCommand = new MembershipsCommand()
        membershipsCommand.memberships = Membership.findAllByUser(userInstance)
        [userInstance: userInstance, membershipsCommand: membershipsCommand, organizations: Organization.findAll()]
    }

    def update() {
        Long id = params.user.long("id")
        Long version = params.user.long("version")
        def membershipsCommand = new MembershipsCommand()
        bindData(membershipsCommand, params.membershipsCommand)
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'user.label', default: 'User')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance,
                        membershipsCommand: membershipsCommand,
                        organizations: Organization.findAll()])
                return
            }
        }

        userInstance.properties = params.user

        if (!userService.saveWithOrganizations(userInstance, membershipsCommand.memberships*.organization)) {
            render(view: "edit", model: [userInstance: userInstance,
                    membershipsCommand: membershipsCommand,
                    organizations: Organization.findAll()])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete() {
        Long id = params.user.long("id")
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (!userService.deleteWithMemberships(userInstance)) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
            return
        }

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
        redirect(action: "list")
    }
}
