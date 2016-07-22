package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.AppStatus
import com.redhat.theses.events.ApplicationEvent
import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class NotificationsIntegrationSpec extends IntegrationSpec {

    def setupSpec() {
        def parameters = [type: Type.BACHELOR,
            university: University.findByName("First"),
            topic: Topic.findByTitle('Automatic waitress robot'),
            note: "Test",
            status: AppStatus.PENDING,
            thesis: new Thesis().save(flush: true),
            applicant: User.findByEmail("example1@example.com")
        ]
        def application = new Application(parameters).save(failOnError: true, flush: true)

        application.event([topic: "applicationCreated",
            data: new ApplicationEvent(application, parameters.applicant),
            params: [fork: true]]).get()
    }

    def cleanupSpec() {
        Feed.last()?.delete(flush: true)
        Notification.last()?.delete(flush: true)
        Notification.last()?.delete(flush: true)
    }

    @Unroll("Will #email, who is #role, get notification when application is created, expected: #notification")
    void "Testing notifications"() {
        given:
            def user = User.findByEmail(email)
        expect:
            (Notification.findByUser(user, [sort: "dateCreated", order: "desc"]) != null) == notification
        where:
            email | role | notification
            "owner1@example.com" | "owner" | true
            "supervisor2@example.com" | "supervisor" | true
            "supervisor1@example.com" | "supervisor for wrong university" | false
            "example2@example.com" | "random user" | false
            "admin@example.com" | "admin" | false
    }
}
