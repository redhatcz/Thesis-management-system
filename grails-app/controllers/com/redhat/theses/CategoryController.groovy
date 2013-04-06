package com.redhat.theses

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class CategoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create() {
        [categoryInstance: new Category(params.category)]
    }

    def save() {
        def categoryInstance = new Category(params.category)
        if (!categoryInstance.save(flush: true)) {
            render(view: "create", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'category.created', args: [categoryInstance.title])
        redirect(controller: 'topic', action: "list")
    }

    def edit(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'category.not.found', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

    def update() {
        Long id = params.category.long("id")
        Long version = params.category.long("version")
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'category.not.found', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        if (version != null && categoryInstance.version > version) {
            categoryInstance.errors.rejectValue("version", "category.optimistic.lock.error")
            render(view: "edit", model: [categoryInstance: categoryInstance])
            return
        }

        categoryInstance.properties = params.category

        if (!categoryInstance.save(flush: true)) {
            render(view: "edit", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'category.updated', args: [categoryInstance.id])
        redirect(controller: 'topic', action: "list")
    }

    def delete() {
        Long id = params.category.long("id")
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'category.not.found', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.message = message(code: 'category.deleted', args: [id])
            redirect(controller: 'topic', action: "list")
        } catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'category.not.deleted', args: [id])
            redirect(controller: 'topic', action: "list")
        }
    }
}
