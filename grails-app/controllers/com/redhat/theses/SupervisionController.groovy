package com.redhat.theses

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_SUPERVISOR'])
class SupervisionController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.SupervisionService
     */
    def supervisionService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def manage(Long id, UniversityCommand universityCommand){
        def topicInstance = Topic.findById(id);

        if (topicInstance == null){
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(controller: 'topic', action: "list")
            return
        }

        def user = springSecurityService.currentUser
        List universities = topicInstance.universities?.sort {it.name}
        universityCommand.universities += Supervision.findAllByTopicAndSupervisor(topicInstance, user)*.university
        [topicInstance: topicInstance, universityCommand: universityCommand, universities: universities]
    }

    def save(){
        Long id = params.topic?.long("id")
        def topicInstance = Topic.get(id)
        if (!topicInstance) {
            flash.message = message(code: 'topic.not.found', args: [id])
            redirect(action: "list")
            return
        }

        def user = springSecurityService.currentUser
        def universityCommand = new UniversityCommand()
        bindData(universityCommand, params.universityCommand)
        universityCommand.validate()

        def supervisions = []
        universityCommand.universities.each {
            if (it?.id) {
                supervisions << new Supervision(topic: topicInstance, supervisor: user, university: it)
            }
        }

        if (universityCommand.hasErrors() || !supervisionService.saveMany(supervisions, user, topicInstance)) {
            render(view: "manage", model: [topicInstance: topicInstance, universityCommand:
                    universityCommand, universities: topicInstance.universities])
            return
        }

        flash.message = message(code: 'supervision.updated', args: [topicInstance.id])
        redirect(controller: 'topic', action: 'show', id: topicInstance.id)

    }

}
