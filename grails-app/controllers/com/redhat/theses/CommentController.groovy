package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.events.CommentEvent
import com.redhat.theses.util.Commons
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

@Secured(['isAuthenticated()'])
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
            def message = richg.alert type: 'error', code: 'comment.not.created'
            render([success: false, message: message] as JSON)
            return
        }

        event("commentCreated", new CommentEvent(comment))

        flash.message = message(code: 'comment.created')
        render([success: true] as JSON)
    }

    def update() {
        Long id = params.comment.int('id')
        Comment comment = Comment.get(id)

        if (!comment) {
            def message = richg.alert type: 'error', code: 'comment.not.found', args: [id]
            render([success: false, message: message] as JSON)
            return
        }

        comment.properties = params.comment

        if (springSecurityService.currentUser != comment.user &&
                !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER')) {
            def message = richg.alert type: 'error', code: 'security.denied.action.message'
            render([success: false, message: message] as JSON)
            return
        }

        if (!comment.content) {
            def message = richg.alert type: 'error', code: 'comment.empty.error'
            render([success: false, message: message] as JSON)
            return
        }

        if (!comment.save(flush: true)) {
            def message = richg.alert type: 'error', code: 'comment.not.updated'
            render([success: false, message: message] as JSON)
            return
        }

        flash.message = message(code: 'comment.updated')
        render([success: true] as JSON)
    }

    def delete() {
        Long id = params.comment.int('id')
        Comment comment = Comment.get(id)

        if (!comment) {
            def message = richg.alert type: 'error', code: 'comment.not.found', args: [id]
            render([success: false, message: message] as JSON)
            return
        }

        if (springSecurityService.currentUser != comment.user &&
                !SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN, ROLE_OWNER')) {
            def message = richg.alert type: 'error', code: 'security.denied.action.message'
            render([success: false, message: message] as JSON)
            return
        }

        if (!Commons.delete(comment)) {
            def message = richg.alert type: 'error', code: 'comment.not.deleted'
            render([success: false, message: message] as JSON)
            return
        }

        flash.message = message(code: 'comment.deleted')
        render([success: true] as JSON)
    }
}
