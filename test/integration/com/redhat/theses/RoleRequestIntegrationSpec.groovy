package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.auth.Role
import com.redhat.theses.AppStatus
import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import spock.lang.Shared
import spock.lang.Unroll

class RoleRequestIntegrationSpec extends IntegrationSpec {
    @Shared controller = new RoleRequestController()

    def setupSpec() {
        SpringSecurityUtils.reauthenticate("example1@example.com", "example2")
    }

    def cleanupSpec() {
        Feed.getAll()*.delete(flush: true)
        Notification.getAll()*.delete(flush: true)
    }

    @Unroll("Can list role requests with filter #customFilter, should return #expectedCount entries")
    void "Test list"() {
        given: "parameters to filter"
            controller.params.filter = [:]
            controller.params.filtering = true
            controller.params.filter << customFilter

            def model = controller.list()
        expect: 
            model?.requestInstanceList?.size() == expectedCount
        where: 
            customFilter | expectedCount
            [status: AppStatus.PENDING] | 2
            [status: AppStatus.DECLINED] | 1
            [applicant: User.findByEmail("example1@example.com")] | 1
            [applicant: User.findByEmail("example2@example.com")] | 1
    }

   @Unroll("Can not decline application #id as #username, should redirect to #redirectUrl")
    void "Can not decline role request"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.decline(id)
        expect:
            controller.response?.redirectUrl.endsWith("list")
        where:
            username | password | id
            "owner1@example.com" | "owner1" | 1
            "supervisor1@example.com" | "supervisor1" | 2
            "example1@example.com" | "example2" | 2
    }

    void "Can decline role request as administrator"() {
        given:
            SpringSecurityUtils.reauthenticate("admin@example.com", "admin")
        when:
            controller.decline(1)
        then:
            RoleRequest.get(1)?.status == AppStatus.DECLINED
            controller.flash.message != null
            controller.response?.redirectUrl.endsWith("list")
    }

    void "Can approve role request as administrator"() {
        given:
            SpringSecurityUtils.reauthenticate("admin@example.com", "admin")
        when:
            controller.approve(1)
        then:
            controller.flash.message != null
            RoleRequest.get(1).applicant.roles.contains(Role.SUPERVISOR)
    }

    @Unroll("Can not approve role request #id as #username, should redirect to #redirectUrl")
    void "Can not approve role request"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.approve(id)
        expect:
            controller.modelAndView?.viewName == null
            controller.response?.redirectUrl?.endsWith("list")
            controller.flash.message != null
        where:
            username | password | id
            "example1@example.com" | "example2" | 1
            "supervisor1@example.com" | "supervisor1" | 2
            "admin@example.com" | "admin" | 4
    }

    @Unroll("Can show role request #id as #username")
    void "Can show role request"() {
        given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.show(id)
        expect:
            controller.flash.message == null
        where:
            username | password | id
            "example1@example.com" | "example2" | 1
            "example3@example.com" | "example3" | 3
            "admin@example.com" | "admin" | 2
            "admin@example.com" | "admin" | 3
    }

    @Unroll("Cannot show role request #id as #username with bad permissions")
    void "Cannot show role request with bad permissions"() {
       given:
            SpringSecurityUtils.reauthenticate(username, password)
            controller.show(id)
        expect:
            controller.response?.redirectUrl?.endsWith("list")
            controller.flash.message != null
        where:
            username | password | id
            "example1@example.com" | "example2" | 2
            "owner1@example.com" | "owner2" | 3
            "supervisor1@example.com" | "supervisor2" | 3
    }
}
