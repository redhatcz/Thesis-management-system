package com.redhat.theses

import com.redhat.theses.util.Util

class OrganizationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    /**
     * Dependency injection of com.redhat.theses.OrganizationService
     */
    def organizationService

    /**
     * Dependency injection of org.codehaus.groovy.grails.commons.GrailsApplication
     */
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

        flash.message = message(code: 'organization.created', args: [organizationInstance.id])
        redirect(action: "show", id: organizationInstance.id)
    }

    def show(Long id) {
        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'organization.not.found', args: [id])
            redirect(action: "list")
            return
        }

        [organizationInstance: organizationInstance]
    }

    def edit(Long id) {
        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'organization.not.found', args: [id])
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
            flash.message = message(code: 'organization.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (version != null && organizationInstance.version > version) {
            organizationInstance.errors.rejectValue("version", "organization.optimistic.lock.error")
            render(view: "edit", model: [organizationInstance: organizationInstance])
            return
        }

        organizationInstance.properties = params.organization

        if (!organizationInstance.save()) {
            render(view: "edit", model: [organizationInstance: organizationInstance])
            return
        }

        flash.message = message(code: 'organization.updated', args: [organizationInstance.id])
        redirect(action: "show", id: organizationInstance.id)
    }

    def delete() {
        Long id = params.organization.long("id")

        def organizationInstance = Organization.get(id)
        if (!organizationInstance) {
            flash.message = message(code: 'organization.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (organizationService.delete(organizationInstance)) {
            flash.message = message(code: 'organization.deleted', args: [id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'organization.not.deleted', args: [id])
            redirect(action: "show", id: id)
        }
    }
}
