package com.redhat.theses

import com.redhat.theses.util.Util

class HomeController {

    static allowedMethods = [index: 'GET']

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    def index() {
        if (Util.isControllerOrActionInUrl(request, 'home', 'index')) {
            redirect uri: '/', permanent: true, params: params
        }

        def yourTheses = null
        if (springSecurityService.isLoggedIn()) {
            yourTheses = Thesis.findAllByAssignee(springSecurityService.currentUser, [sort:'dateCreated', order:'desc'])
        }

        params.sort = 'dateCreated'
        params.order= 'desc'
        params.max = Util.max(params.max)
        render view: '/index', model:
                [feedList: Feed.findAll(params), feedListTotal: Feed.count(), yourTheses: yourTheses]
    }
}
