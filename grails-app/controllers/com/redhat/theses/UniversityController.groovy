package com.redhat.theses

import org.springframework.dao.DataIntegrityViolationException

class UniversityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [universityInstanceList: University.list(params), universityInstanceTotal: University.count()]
    }

    def create() {
        [universityInstance: new University(params)]
    }

    def save() {
        def universityInstance = new University(params)
        if (!universityInstance.save(flush: true)) {
            render(view: "create", model: [universityInstance: universityInstance])
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

        [universityInstance: universityInstance]
    }

    def edit(Long id) {
        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
            return
        }

        [universityInstance: universityInstance]
    }

    def update(Long id, Long version) {
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

        universityInstance.properties = params

        if (!universityInstance.save(flush: true)) {
            render(view: "edit", model: [universityInstance: universityInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'university.label', default: 'University'), universityInstance.id])
        redirect(action: "show", id: universityInstance.id)
    }

    def delete(Long id) {
        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
            return
        }

        try {
            universityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'university.label', default: 'University'), id])
            redirect(action: "show", id: id)
        }
    }
}
