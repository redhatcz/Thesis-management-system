package com.redhat.theses

import com.redhat.theses.util.Util

class HomeController {

    static allowedMethods = [index: 'GET']

    def index() {
        if (Util.isControllerOrActionInUrl(request, 'home', 'index')) {
            redirect uri: '/', permanent: true, params: params
        }

        params.sort = 'dateCreated'
        params.order= 'desc'
        params.max = Util.max(params.max)
        render view: '/index', model: [feedList: Feed.findAll(params), feedListTotal: Feed.count()]
    }
}
