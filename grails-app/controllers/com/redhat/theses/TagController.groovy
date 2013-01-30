package com.redhat.theses

import org.springframework.dao.DataIntegrityViolationException
import com.redhat.theses.util.Util

class TagController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        [tagInstanceList: Tag.list(params), tagInstanceTotal: Tag.count()]
    }

    def create() {
        [tagInstance: new Tag(params.tag)]
    }

    def save() {
        def tagInstance = new Tag(params.tag)
        if (!tagInstance.save(flush: true)) {
            render(view: "create", model: [tagInstance: tagInstance])
            return
        }

        flash.message = message(code: 'tag.created', args: [tagInstance.id])
        redirect(action: "show", id: tagInstance.id)
    }

    def show(Long id) {
        def tagInstance = Tag.get(id)
        if (!tagInstance) {
            flash.message = message(code: 'tag.not.found', args: [id])
            redirect(action: "list")
            return
        }

        [tagInstance: tagInstance]
    }

    def edit(Long id) {
        def tagInstance = Tag.get(id)
        if (!tagInstance) {
            flash.message = message(code: 'tag.not.found', args: [id])
            redirect(action: "list")
            return
        }

        [tagInstance: tagInstance]
    }

    def update() {
        Long id = params.tag.long("id")
        Long version = params.tag.long("version")
        def tagInstance = Tag.get(id)
        if (!tagInstance) {
            flash.message = message(code: 'tag.not.found', args: [id])
            redirect(action: "list")
            return
        }

        if (version != null && tagInstance.version > version) {
            tagInstance.errors.rejectValue("version", "tag.optimistic.lock.error")
            render(view: "edit", model: [tagInstance: tagInstance])
            return
        }

        tagInstance.properties = params.tag

        if (!tagInstance.save(flush: true)) {
            render(view: "edit", model: [tagInstance: tagInstance])
            return
        }

        flash.message = message(code: 'tag.updated', args: [tagInstance.id])
        redirect(action: "show", id: tagInstance.id)
    }

    def delete() {
        Long id = params.tag.long("id")
        def tagInstance = Tag.get(id)
        if (!tagInstance) {
            flash.message = message(code: 'tag.not.found', args: [id])
            redirect(action: "list")
            return
        }

        try {
            tagInstance.delete(flush: true)
            flash.message = message(code: 'tag.deleted', args: [id])
            redirect(action: "list")
        } catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'tag.not.deleted', args: [id])
            redirect(action: "show", id: id)
        }
    }
}
