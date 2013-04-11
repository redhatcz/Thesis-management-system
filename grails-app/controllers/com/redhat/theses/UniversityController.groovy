package com.redhat.theses

import com.redhat.theses.util.Util
import grails.plugins.springsecurity.Secured

class UniversityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static Long UNIVERSITY_MAX_TOPICS = 10

    /**
     * Dependency injection of com.redhat.theses.UniversityService
     */
    def universityService

    /**
     * Dependency injection of com.redhat.theses.CommentService
     */
    def commentService

    static final Integer MAX_UNIVERSITIES = 20

    static defaultAction = "list"

    def list(Integer max) {
        params.max = Util.max(max, MAX_UNIVERSITIES)
        [universityInstanceList: University.list(params), universityInstanceTotal: University.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        [universityInstance: new University(params.university)]
    }

    @Secured(['ROLE_ADMIN'])
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

        def topicInstanceList = Topic.findAllByUniversity(universityInstance, [max: UNIVERSITY_MAX_TOPICS])
        def commentCounts = commentService.countByArticles(topicInstanceList)

        [universityInstance: universityInstance, topicInstanceList: topicInstanceList, commentCounts: commentCounts]
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        def universityInstance = University.get(id)
        if (!universityInstance) {
            flash.message = message(code: 'university.not.found', args: [id])
            redirect(action: "list")
            return
        }
        [universityInstance: universityInstance]
    }

    @Secured(['ROLE_ADMIN'])
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

    @Secured(['ROLE_ADMIN'])
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
