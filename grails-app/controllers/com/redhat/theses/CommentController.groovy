package com.redhat.theses

import com.redhat.theses.auth.User
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

//TODO: error messages
class CommentController {
    static allowedMethods = [create: 'POST', update: 'POST', delete: 'POST']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    def create() {
        Comment comment = new Comment(params.comment)
        comment.setUser((User) springSecurityService.currentUser)

        if (!comment.content) {
            def message = richg.alert type: 'error', code: 'comment.empty.error'
            render([success: false, message: message] as JSON)
            return
        }

        if (!comment.save(flush: true)) {
            def message = richg.alert type: 'error', code: 'comment.error'
            render([success: false, message: message] as JSON)
            return
        }

        flash.message = message(code: 'comment.created')
        render([success: true] as JSON)
    }

    def update() {
        Long id = params.comment.int('id')
        Comment comment = Comment.get(id)
        comment.properties = params.comment

        if (!comment.content) {
            def message = richg.alert type: 'error', code: 'comment.empty.error'
            render([success: false, message: message] as JSON)
            return
        }

        if (!comment.save(flush: true)) {
            def message = richg.alert type: 'error', code: 'comment.error'
            render([success: false, message: message] as JSON)
            return
        }

        flash.message = message(code: 'comment.updated')
        render([success: true] as JSON)
    }

    def delete() {
        Long id = params.comment.int('id')
        Comment comment = Comment.get(id)

        //TODO: some error might occur
        comment.delete()

        flash.message = message(code: 'comment.deleted')
        render([success: true] as JSON)
    }
}
