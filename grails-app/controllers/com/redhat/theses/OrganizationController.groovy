package com.redhat.theses

import com.redhat.theses.util.Util

class OrganizationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def organizationService

    def grailsApplication

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        [organizationInstanceList: Organization.list(params), organizationInstanceTotal: Organization.count()]
    }

    def create() {
        [organizationInstance: new Organization(params.organization)]
    }

    def save() {
        def organizationClass = grailsApplication.domainClasses.find {it.name == params.organization.type}
        def organizationInstance = (Organization) organizationClass.newInstance()
        organizationInstance.properties = params.organization

        if (!organizationInstance.save()) {
            render(view: "create", model: [organizationInstance: organizationInstance])
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
        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
            return
        }
        [organizationInstance: organizationInstance]
    }

    def update() {
        Long id = params.organization.long("id")
        Long version = params.organization.long("version")

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

        if (!organizationInstance.save()) {
            render(view: "edit", model: [organizationInstance: organizationInstance])
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

        if (organizationService.deleteWithMemberships(organizationInstance)) {
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), id])
            redirect(action: "show", id: id)
        }
    }
}
