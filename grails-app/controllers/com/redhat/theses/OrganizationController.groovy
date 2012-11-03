package com.redhat.theses

import com.redhat.theses.auth.User
import grails.converters.JSON

class OrganizationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def organizationService

    def grailsApplication

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [organizationInstanceList: Organization.list(params), organizationInstanceTotal: Organization.count()]
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
        [organizationInstance: new Organization(params.organization), usersCommand: new UsersCommand()]
    }

    def save() {
        def usersCommand = new UsersCommand()
        bindData(usersCommand, params.usersCommand)
        def organizationClass = grailsApplication.domainClasses.find {it.name == params.organization.type}
        def organizationInstance = (Organization) organizationClass.newInstance()
        organizationInstance.properties = params.organization

        usersCommand.users = usersCommand.users.unique().findAll { it?.id }
        def memberships = usersCommand.users.collect { new Membership(organization: organizationInstance, user: it) }
        if (!usersCommand.validate() || !organizationService.saveWithMemberships(organizationInstance, memberships)) {
            render(view: "create", model: [organizationInstance: organizationInstance, usersCommand: usersCommand])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])
        redirect(action: "show", id: organizationInstance.id)
    }

    def show(Long id) {
        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
            return
        }

        [organizationInstance: organizationInstance, users: organizationInstance.users]
    }

    def edit(Long id) {
        def usersCommand = new UsersCommand()
        bindData(usersCommand, params.usersCommand)

        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
            return
        }
        usersCommand.users = Membership.findAllByOrganization(organizationInstance).collect { it.user }
        [organizationInstance: organizationInstance, usersCommand: usersCommand]
    }

    def update() {
        Long id = params.organization.long("id")
        Long version = params.organization.long("version")
        def usersCommand = new UsersCommand()
        bindData(usersCommand, params.usersCommand)

        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (organizationInstance.version > version) {
                organizationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'organization.label', default: 'Organization')] as Object[],
                          "Another user has updated this Organization while you were editing")
                render(view: "edit", model: [organizationInstance: organizationInstance])
                return
            }
        }

        organizationInstance.properties = params.organization

        usersCommand.users = usersCommand.users.unique().findAll { it?.id }
        def memberships = usersCommand.users.collect { new Membership(organization: organizationInstance, user: it) }

        if (!usersCommand.validate() || !organizationService.saveWithMemberships(organizationInstance, memberships)) {
            render(view: "edit", model: [organizationInstance: organizationInstance, usersCommand: usersCommand])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])
        redirect(action: "show", id: organizationInstance.id)
    }

    def delete() {
        Long id = params.organization.long("id")

        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
            return
        }

        if (organizationService.deleteWithMemberships(organizationInstance, Membership.findAllByOrganization(organizationInstance))) {
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "show", id: id)
        }
    }
}
