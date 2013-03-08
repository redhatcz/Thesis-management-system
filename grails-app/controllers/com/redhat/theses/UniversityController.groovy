package com.redhat.theses

import com.redhat.theses.util.Util

class UniversityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    /**
     * Dependency injection of com.redhat.theses.UniversityService
     */
    def universityService

    /**
     * Dependency injection of org.codehaus.groovy.grails.commons.GrailsApplication
     */
    def grailsApplication

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        [universityInstanceList: University.list(params), universityInstanceTotal: University.count()]
    }

    def create() {
        [universityInstance: new University(params.university)]
    }

    def save() {
        def universityInstance = new University(params.university)

        if (!universityInstance.save()) {
            render(view: "create", model: [universityInstance: universityInstance])
            return
        }

        flash.message = message(code: 'university.created', args: [universityInstance.id])
        redirect(action: "show", id: universityInstance.id)
    }

    def show(Long id) {
        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'university.not.found', args: [id])
            redirect(action: "list")
            return
        }

        [universityInstance: universityInstance]
    }

    def edit(Long id) {
        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'university.not.found', args: [id])
            redirect(action: "list")
            return
        }
        [universityInstance: universityInstance]
    }

    def update() {
        Long id = params.university.long("id")
        Long version = params.university.long("version")

        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'university.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (version != null && universityInstance.version > version) {
            universityInstance.errors.rejectValue("version", "university.optimistic.lock.error")
            render(view: "edit", model: [universityInstance: universityInstance])
            return
        }

        universityInstance.properties = params.university

        if (!universityInstance.save()) {
            render(view: "edit", model: [universityInstance: universityInstance])
            return
        }

        flash.message = message(code: 'university.updated', args: [universityInstance.id])
        redirect(action: "show", id: universityInstance.id)
    }

    def delete() {
        Long id = params.university.long("id")

        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'university.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (universityService.delete(universityInstance)) {
            flash.message = message(code: 'university.deleted', args: [id])
            redirect(action: "list")
        } else {
            flash.message = message(code: 'university.not.deleted', args: [id])
            redirect(action: "show", id: id)
        }
    }
}
