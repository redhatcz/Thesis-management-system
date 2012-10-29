package com.redhat.theses

import com.redhat.theses.auth.User
import grails.converters.JSON

class UniversityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def universityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [universityInstanceList: University.list(params.university), universityInstanceTotal: University.count()]
    }

    def listUsersByName(String term) {
        def users = User.findAllByFullNameLike("%${term}%", [max: 5])
        def userList = []
        users.each {
            userList << [id: it.id, label: it.fullName, name: it.fullName]
        }
        render userList as JSON
    }

    def create() {
        [universityInstance: new University(params.university), usersCommand: new UsersCommand()]
    }

    def save() {
        def usersCommand = new UsersCommand()
        bindData(usersCommand, params.usersCommand)
        def universityInstance = new University(params.university)

        def filteredUsers = usersCommand.users.unique().grep { it.id != null }
        def memberships = filteredUsers.collect { new Membership(university: universityInstance, user: it) }
        if (!usersCommand.validate() || !universityService.saveWithMemberships(universityInstance, memberships)) {
            render(view: "create", model: [universityInstance: universityInstance, usersCommand: usersCommand])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'university.label', default: 'University'), universityInstance.id])
        redirect(action: "show", id: universityInstance.id)
    }

    def show(Long id) {
        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
            return
        }

        [universityInstance: universityInstance, users: universityInstance.users]
    }

    def edit(Long id) {
        def usersCommand = new UsersCommand()
        bindData(usersCommand, params.usersCommand)

        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
            return
        }
        usersCommand.users = Membership.findAllByUniversity(universityInstance).collect { it.user }
        [universityInstance: universityInstance, usersCommand: usersCommand]
    }

    def update() {
        Long id = params.university.long("id")
        Long version = params.university.long("version")
        def usersCommand = new UsersCommand()
        bindData(usersCommand, params.usersCommand)

        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (universityInstance.version > version) {
                universityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'university.label', default: 'University')] as Object[],
                          "Another user has updated this University while you were editing")
                render(view: "edit", model: [universityInstance: universityInstance])
                return
            }
        }

        universityInstance.properties = params.university

        def filteredUsers = usersCommand.users.unique().grep { it.id != null }
        def memberships = filteredUsers.collect { new Membership(university: universityInstance, user: it) }

        if (!usersCommand.validate() || !universityService.saveWithMemberships(universityInstance, memberships)) {
            render(view: "edit", model: [universityInstance: universityInstance, usersCommand: usersCommand])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'university.label', default: 'University'), universityInstance.id])
        redirect(action: "show", id: universityInstance.id)
    }

    def delete() {
        Long id = params.university.long("id")

        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
            return
        }

        if (universityService.deleteWithMemberships(universityInstance, Membership.findAllByUniversity(universityInstance))) {
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "show", id: id)
        }
    }
}
