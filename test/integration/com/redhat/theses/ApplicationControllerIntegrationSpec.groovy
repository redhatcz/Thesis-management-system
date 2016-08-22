package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.AppStatus
import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import spock.lang.Shared
import spock.lang.Unroll

class ApplicationControllerIntegrationSpec extends IntegrationSpec {
    @Shared controller = new ApplicationController()

    def setupSpec() {
        SpringSecurityUtils.reauthenticate("example1@example.com", "example2")
    }

    def cleanupSpec() {
        Feed.getAll()*.delete(flush: true)
        Notification.getAll()*.delete(flush: true)
    }

    @Unroll("Can list applications with filter #customFilter and maximum #max entries, should return #expectedCount entries")
    void "Test list"() {
        given: "parameters to filter"
            controller.params.filter = [:]
            controller.params.filtering = true
            controller.params.filter << customFilter

            def model = controller.list(max)
        expect: 
            model?.applicationInstanceList?.size() == expectedCount
        where: 
            customFilter | max | expectedCount
            [status: AppStatus.PENDING] | 5 | 4
            [applicant: User.findByEmail("example2@example.com")] | 15 | 3
            [applicant: User.findByEmail("example2@example.com")] | 5 | 3
            [type: Type.DIPLOMA] | 10 | 3
            [type: AppStatus.PENDING] | 1 | 1
    }

    void "Can create application from existing topic"() {
        when: 
            def model = controller.create(1)
        then:
            model?.universities?.size() == 1
            model?.applicationInstance != null
    }

    @Unroll("Can not create application from topic #id, expected redirect to #url")
    void "Can not create application from non-existent topic"() {
        given:
            def model = controller.create(id)
        expect:
            model == null
            controller.response?.redirectUrl == url
            controller.flash.message != null
        where:
            id | url
            4 | "/topic/list"
            15 | "/topic/list"
            5 | "/topic/show/5"
    }

    void "Can save application"() {
        given: "parameters to save"
            controller.request.method = "POST"
            controller.params.application = [:]
            controller.params.application.type = Type.DIPLOMA
            controller.params.application.university = University.findByName("Second")
            controller.params.application.topic = Topic.findByTitle('Test everything')
            controller.params.application.note = "Test"
            controller.params.application.thesis = new Thesis().save(flush: true)
        when: "trying to save it"
            controller.save()
        then: "it should succeed"
            controller.modelAndView?.viewName == null
            controller.flash.message != null
    }

    void "Can not save without proper parameters"() {
        given: "some, but not all parameters"
            controller.params.application = [:]
            controller.params.application.topic = Topic.findByTitle('Thesis management system')
        when: "trying to save"
            controller.save()
        then: "should fail"
            controller.modelAndView?.viewName?.endsWith("create")
            controller.flash?.message == null
            controller.modelAndView?.model?.universities?.size() == 1
    }

    @Unroll("Can not decline application #id out of as #username, should redirect to #redirectUrl")
    void "Can not decline application"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.decline(id)
        expect:
            controller.response?.redirectUrl.endsWith(redirectUrl)
        where:
            username | password | id | redirectUrl
            "owner1@example.com" | "owner1" | 15 | "list"
            "example1@example.com" | "example2" | 2 | "list"
    }

    @Unroll("Can decline application #id with #username")
    void "Can decline application"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.decline(id)
        expect:
            Application.get(id)?.status == AppStatus.DECLINED
            controller.flash.message != null
            controller.response?.redirectUrl.endsWith("list")
        where:
            username | password | id
            "owner1@example.com" | "owner1" | 1
            "supervisor1@example.com" | "supervisor1" | 4
            "supervisor1@example.com" | "supervisor1" | 2
    }

    @Unroll("Can approve application #id with proper permissions as #username")
    void "Can approve application"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            def model = controller.approve(id)
        expect:
            controller.flash.message == null
            model?.thesisInstance?.assignee == Application.get(id).applicant
        where:
            username | password | id
            "admin@example.com" | "admin" | 1
            "supervisor2@example.com" | "supervisor1" | 1
            "owner1@example.com" | "owner2" | 4
    }

    @Unroll("Can not approve application #id as #username, should redirect to #redirectUrl")
    void "Can not approve application"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.approve(id)
        expect:
            controller.modelAndView?.viewName == null
            controller.response?.redirectUrl?.endsWith(redirectUrl)
            controller.flash.message != null
        where:
            username | password | id | redirectUrl
            "example1@example.com" | "example2" | 4 | "list"
            "admin@example.com" | "admin" | 15 | "list"
            "admin@example.com" | "admin" | 2 | "show/2"
    }

    void "Can show application"() {
        when: "trying to show application"
            def model = controller.show(1)
        then: "should show application"
            model?.applicationInstance != null
    }

    void "Cannot show non-existent application"() {
        when: "trying to show non-existent application"
            def model = controller.show(Application.count() + 1)
        then: "should fail"
            model == null
    }

    @Unroll("Can approve and save application #id")
    void "Can approve and save application"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)

            def application = Application.get(id)

            controller.params.thesis = [:]
            controller.params.thesis.assignee = application.applicant
            controller.params.thesis.university = application.university
            controller.params.thesis.title = "title"
            controller.params.thesis.type = Type.DIPLOMA
            controller.params.thesis.topic = application.topic
            controller.params.thesis.description = "tst"

            def model = controller.approveSave(id)
        expect:
            model == null
            controller.response?.redirectUrl.matches("\\/thesis\\/show\\/\\d+\\/" + controller.params.thesis.title)
            controller.flash.message != null
        where:
            username | password | id
            "admin@example.com" | "admin" | 1
            "supervisor1@example.com" | "supervisor1" | 5
            "owner1@example.com" | "owner2" | 4
    }

}
