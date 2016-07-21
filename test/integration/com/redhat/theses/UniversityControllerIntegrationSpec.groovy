package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.auth.Role
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared
import spock.lang.Unroll
import grails.test.mixin.*

class UniversityControllerIntegrationSpec extends IntegrationSpec {
    @Shared controller = new UniversityController()

    def setupSpec() {
    }

    def cleanup() {
    }

    void "Can retrieve university"() {
        when: "retrieving university by ID"
            def model = controller.show(1)
        then: "should retrieve correct university"
            model?.universityInstance?.name == "First"
            model?.universityInstance?.acronym == "F"
    }
    
    void "Can not retrieve non-existent university"() {
        when: "trying to retrieve university by ID"
            def model = controller.show(4)
        then: "should retrieve nothing and redirect to list of universities"
            model == null
            controller.flash.message != null
            controller.response?.redirectUrl.endsWith("list")
    }
    
    void "Can not edit non-existent university"() {
        when: "trying to edit university"
            def model = controller.edit(4)
        then: "should fail and redirect to list of universities"
            model == null
            controller.flash.message != null
            controller.response.redirectUrl.endsWith("list")
    }
    
    void "Can edit university"() {
        when: "trying to edit university"
            def model = controller.edit(2)
        then: "should retrieve right university to edit"
            model?.universityInstance?.name == "Second"
            model?.universityInstance?.acronym == "S"
    }
    
    void "Can delete university"() {
        given: "existing university"
            controller.request.method = "POST"
            def p = ['id': "3"]
            def request = [getParameterMap: { -> p }] as javax.servlet.http.HttpServletRequest    
            controller.params.university = new org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap(request)
        when: "trying to delete it"
            controller.delete()
        then: "university should be deleted"
            University.list().size == 2
    }
    
    void "Can not delete non-existent university"() {
        given: "non-existing university"
            controller.request.method = "POST"
            def p = ['id': "4"]
            def request = [getParameterMap: { -> p }] as javax.servlet.http.HttpServletRequest
    
            controller.params.university = new org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap(request)
        when: "trying to delete it"
            controller.delete()
        then: "shouldn't delete anything"
            University.list().size == 3
    }
    
    @Unroll("Can retrieve list of universities with maximum: #max, should return #expectedCount universities")
    void "Can retrieve list of universities"() {
        expect:
            def model = controller.list(max)
            model.universityInstanceList.size() == expectedCount
        where:
            max | expectedCount
            1 | 1
            2 | 2
            4 | 3
    }
    
    void "Can save university"() {
        given: "right parameters to save"
            controller.request.method = "POST"
            controller.params.university = [:]
            controller.params.university.name = "Testovací"
            controller.params.university.acronym = "tst"
        when: "Trying to save it"
            controller.save()
        then: "created University should match the info and redirect to show page"
            controller.flash.message != null
            controller.response.redirectedUrl.endsWith("show/4")
    }
    
    void "Can not save non-existent university"() {
        when: "Trying to save with bad parameters"
            controller.request.method = "POST"
            controller.save()
        then: "should fail and redirect to create page"
            controller.modelAndView.viewName.endsWith("create")
    }
}
