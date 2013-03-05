package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util

class MyThesisController {

    def springSecurityService
    def uploadHandlerService
    def gridFileService


    def edit(Long id) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(controller: 'thesis', action: "list")
            return
        }

        User user = springSecurityService.currentUser
        if (thesisInstance.assignee != user) {
            flash.message = message(code: 'action.permission.denied')
            redirect(controller: 'thesis', action: "list")
            return
        }

        [thesisInstance: thesisInstance]
    }

    def save() {
        Long id = params.thesis.long("id")
        Long version = params.thesis.long("version")

        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        User user = springSecurityService.currentUser
        if (thesisInstance.assignee != user) {
            flash.message = message(code: 'action.permission.denied')
            redirect(controller: 'thesis', action: "list")
            return
        }

        if (version && thesisInstance.version > version) {
            thesisInstance.errors.rejectValue("version", "thesis.optimistic.lock.error")
            render view: "edit", model:
                    [thesisInstance: thesisInstance]
            return
        }

        bindData(thesisInstance, params, [include: ['thesisAbstract']], 'thesis')
        thesisInstance.properties = params.thesis
        if (!thesisService.save(thesisInstance)) {
            render view: 'edit', model:
                    [thesisInstance: thesisInstance]
            return
        }

        flash.message = message(code: 'thesis.updated', args: [thesisInstance.id])
        redirect action: "show", id: thesisInstance.id
    }


    def show(Long id) {
        def thesisInstance = Thesis.get(id)

        if (!thesisInstance) {
            flash.message = message(code: 'thesis.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def commentsTotal = Comment.countByArticle(thesisInstance)
        def defaultOffset = Util.lastOffset(commentsTotal, params.max, params.offset)

        def comments = Comment.findAllByArticle(thesisInstance,
                [max: Util.max(params.max), sort: 'dateCreated', offset: defaultOffset])

        def subscriber = Subscription.findBySubscriberAndArticle(springSecurityService.currentUser,
                                                                 thesisInstance)

        [thesisInstance: thesisInstance, comments: comments, commentsTotal: commentsTotal,
         subscriber: subscriber, files: gridFileService.getAllFiles(thesisInstance)]
    }
}
