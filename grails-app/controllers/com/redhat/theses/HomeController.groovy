package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util

class HomeController {

    static allowedMethods = [index: 'GET']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.CommentService
     */
    def commentService

    def index() {
        if (Util.isControllerOrActionInUrl(request, 'home', 'index')) {
            redirect uri: '/', permanent: true, params: params
        }

        def yourTheses = null
        if (springSecurityService.isLoggedIn()) {
            yourTheses = Thesis.findAllByAssignee(springSecurityService.currentUser, [sort:'dateCreated', order:'desc'])
        }

        def topicList = Topic.list(sort: 'dateCreated', order: 'desc', max: 5)

        //statistics
        def statistics = [
                topicCount: Topic.count(),
                thesisCount: Thesis.count(),
                thesisSuccessfulCount: Thesis.countByStatus(Status.FINISHED) - Thesis.countByGrade(Grade.F),
                userCount: User.count()
        ]

        def commentCounts = commentService.countByArticles(topicList)

        render view: '/index', model: [topicList: topicList, yourTheses: yourTheses,
             statistics: statistics, commentCounts: commentCounts]
    }
}
