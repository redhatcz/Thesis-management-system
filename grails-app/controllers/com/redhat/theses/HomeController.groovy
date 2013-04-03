package com.redhat.theses

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

    /**
     * Dependency injection of com.redhat.theses.config.Configuration
     */
    def configuration

    static final Long MAX_LATEST_TOPICS = 10

    def index() {
        if (Util.isControllerOrActionInUrl(request, 'home', 'index')) {
            redirect uri: '/', permanent: true, params: params
        }

        def yourTheses = null
        if (springSecurityService.isLoggedIn()) {
            yourTheses = Thesis.findAllByAssignee(springSecurityService.currentUser, [sort:'dateCreated', order:'desc'])
        }

        def topicList = Topic.findAllByEnabled(true, [sort: 'dateCreated', order: 'desc', max: MAX_LATEST_TOPICS])

        def commentCounts = commentService.countByArticles(topicList)

        render view: '/index', model: [topicList: topicList, yourTheses: yourTheses,
             commentCounts: commentCounts, config: configuration.getConfig()]
    }
}
