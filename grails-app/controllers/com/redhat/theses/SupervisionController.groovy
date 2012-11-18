package com.redhat.theses

import grails.plugins.springsecurity.SpringSecurityService
import com.redhat.theses.auth.User
import com.redhat.theses.util.Util

class SupervisionController {

    def springSecurityService
    def topicService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def manage(Long id, MembershipsCommand membershipsCommand){
        def topicInstance = Topic.findById(id);

        if (topicInstance == null){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
            return
        }

        def user = springSecurityService.currentUser
        def memberships = Membership.findAllByUser(User.get(user.id))
                .findAll {it.organization.id in topicInstance.universities*.id}
        membershipsCommand.memberships +=  topicInstance.supervisions
                .collect {it.membership}
                .findAll {it.user.id == user.id}
                .findAll {it.organizationId in topicInstance.universities*.id}
        [topicInstance: topicInstance, membershipCommand: membershipsCommand, memberships: memberships]
    }

    def save(){
        Long id = params.topic?.long("id")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), id])
            redirect(action: "list")
            return
        }

        def user = springSecurityService.currentUser
        def membershipsCommand = new MembershipsCommand()

        // little hack to prevent binding from initialization of elements with null id
        params.remove '_supervisions'

        bindData(membershipsCommand, params.supervisions)
        membershipsCommand.memberships = membershipsCommand.memberships
                .findAll {it.userId == user.id}


        if (!membershipsCommand.validate() || !topicService.saveSupervisions(topicInstance, membershipsCommand.memberships))  {
            def memberships = Membership.findAllByUser(User.get(user.id))
                    .findAll {it.organization.id in topicInstance.universities*.id}
            render(view: "manage", model: [topicInstance: topicInstance, membershipCommand: membershipsCommand, memberships: memberships])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'topic.label', default: 'Topic'), topicInstance.id])
        redirect(controller: 'user', action: 'supervisions', id: user.id)

    }

}
