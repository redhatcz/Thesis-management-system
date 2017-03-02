package com.redhat.theses

import javax.servlet.http.HttpServletRequest

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import com.redhat.theses.auth.User

import grails.converters.JSON
import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared
import spock.lang.Unroll

/**
 * @author phala
 *
 */
class ThesisControllerIntegrationSpec extends IntegrationSpec {
    @Shared controller = new ThesisController()

    @Unroll("Can list thesis with filter #customFilter, should return #expectedCount entries")
    void "Test list"() {
        given: "parameters to filter"
            controller.params.filter = [:]
            controller.params.filtering = true
            controller.params.filter << customFilter

            def model = controller.list()
        expect: 
            model?.thesisInstanceList?.size() == expectedCount
        where: 
            customFilter | expectedCount
            [status: Status.FINISHED] | 2
            [supervisor: User.findByEmail("supervisor1@example.com")] | 1
            [type: Type.BACHELOR] | 1
    }

    @Unroll("Can create new Thesis from topic #id")
    void "Can create thesis"() {
        given: 
            def model = controller.create(id)
        expect:
            model?.thesisInstance != null
            model?.disabledTopicField == field
        where:
            id | field
            1  | true
            null | false
            5125 | false
    }

    void "Can show thesis"() {
        when: "trying to show thesis"
            def model = controller.show(7, "implementation-of-test")
        then: "should show thesis"
            controller.flash.message == null
            model?.thesisInstance != null
    }

    void "Cannot show non-existent thesis"() {
        when: "trying to show thesis"
            def model = controller.show(0, "")
        then: "should show thesis"
            model == null
            controller.flash.message != null
    }

    @Unroll("Can edit thesis #id as #username")
    void "Can edit thesis"() {
        given: "thesis id, username and password"
            SpringSecurityUtils.reauthenticate(username, password)
            def model = controller.edit(id)
        expect: "correct thesis"
            model != null
            model?.thesisInstance != null
        where:
            id | username | password
            8  | "example3@example.com" | "example3"
            7  | "admin@example.com"    | "admin"
            8 | "supervisor2@example.com" | "supervisor2"
    }

    @Unroll("Cannot update thesis #id as #username, should redirect to #url")
    void "Cannot update thesis"() {
        given: "thesis id, username and password"
            def p = ['id': id, "version": Integer.MAX_VALUE]
            def request = [getParameterMap: { -> p }] as HttpServletRequest
            controller.params.thesis = new GrailsParameterMap(request)

            SpringSecurityUtils.reauthenticate(username, password)
            controller.update()
        expect:
            //model != null
            controller.flash.message != null
            controller.response?.redirectUrl.contains(url)
        where:
            username | password | url | id
            "example1@example.com" | "example1" | "show/8" | "8"
            "admin@example.com" | "admin" | "list" | "1"
            "person@gmail.com" | "person" | "show/7" | "7"
    }

    @Unroll("Cannot delete thesis #id as #username, should return #message message")
    void "Cannot delete thesis"() {
        given:
            def request = [getParameterMap: { -> [id: id] }] as HttpServletRequest
            controller.params.thesis = new GrailsParameterMap(request)

            SpringSecurityUtils.reauthenticate(username, password)
            controller.delete()
        expect:
            controller.flash?.message == controller.message(code: message, args: [id])
            controller.response?.redirectUrl.contains("list")
        where:
            id | username | password | message
            2 | "admin@example.com" | "admin" | 'thesis.not.found'
            5 | "admin@example.com" | "admin" | 'thesis.not.found'
            7 | "supervisor2@example.com" | "supervisor2" | 'security.denied.action.message'
            8 | "person@gmail.com" | "person" | 'security.denied.action.message'
    }

    @Unroll("Can delete thesis #id as #username")
    void "Can delete thesis"() {
        given:
            def request = [getParameterMap: { -> [id: id] }] as HttpServletRequest
            controller.params.thesis = new GrailsParameterMap(request)

            SpringSecurityUtils.reauthenticate(username, password)
            controller.delete()
        expect:
            controller.flash?.message == controller.message(code: 'thesis.deleted', args: [id])
            controller.response?.redirectUrl.contains("list")
        where:
            id | username | password
            6 | "admin@example.com" | "admin"
            8 | "supervisor2@example.com" | "supervisor2"
            7 | "owner1@example.com" | "owner1"
    }

    @Unroll("Cannot edit thesis #id notes as #username, should return #message message")
    void "Cannot edit notes"() {
        given:
            def result = [success: false, message: controller.message(code: message, args: [id]), content: null]

            SpringSecurityUtils.reauthenticate(username, password)
            def rendered = controller.notes(id)
        expect:
            rendered == controller.render(text: result as JSON, contentType: 'text/json')
        where:
            id | username | password | message
            2 | "admin@example.com" | "admin" | 'thesis.not.found'
            5 | "admin@example.com" | "admin" | 'thesis.not.found'
            7 | "supervisor2@example.com" | "supervisor2" | 'security.denied.action.message'
            8 | "person@gmail.com" | "person" | 'security.denied.action.message'
    }

    @Unroll("Can edit thesis #id notes as #username")
    void "Can edit notes"() {
        given:
            def thesisInstance = Thesis.get(id)
            def result = [success: true, message: null, content: thesisInstance.notes]

            SpringSecurityUtils.reauthenticate(username, password)
            def rendered = controller.notes(id)
        expect:
            rendered == controller.render(text: result as JSON, contentType: 'text/json')
        where:
            id | username | password
            6 | "admin@example.com" | "admin"
            8 | "supervisor2@example.com" | "supervisor2"
            7 | "owner1@example.com" | "owner1"
    }

    @Unroll("Cannot update thesis #id notes as #username, should return #message message")
    void "Cannot update notes"() {
        given:
            def result = [success: false, message: controller.message(code: message, args: [id]), content: null]
            def request = [getParameterMap: { -> [id: id] }] as HttpServletRequest
            controller.params.thesis = new GrailsParameterMap(request)

            SpringSecurityUtils.reauthenticate(username, password)
            def rendered = controller.updateNotes()
        expect:
            rendered == controller.render(text: result as JSON, contentType: 'text/json')
        where:
            id | username | password | message
            2 | "admin@example.com" | "admin" | 'thesis.not.found'
            5 | "admin@example.com" | "admin" | 'thesis.not.found'
            7 | "supervisor2@example.com" | "supervisor2" | 'security.denied.action.message'
            8 | "person@gmail.com" | "person" | 'security.denied.action.message'
    }

    @Unroll("Can update thesis #id notes as #username")
    void "Can update notes"() {
        given:
            def thesisInstance = Thesis.get(id)
            def request = [getParameterMap: { -> [id: id] }] as HttpServletRequest
            controller.params.thesis = new GrailsParameterMap(request)
            def result = [success: true, message: null, content: thesisInstance.notes]

            SpringSecurityUtils.reauthenticate(username, password)
            def rendered = controller.updateNotes()
        expect:
            rendered == controller.render(text: result as JSON, contentType: 'text/json')
        where:
            id | username | password
            6 | "admin@example.com" | "admin"
            8 | "supervisor2@example.com" | "supervisor2"
            7 | "owner1@example.com" | "owner1"
    }

}
