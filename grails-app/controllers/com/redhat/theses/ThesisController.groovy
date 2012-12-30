package com.redhat.theses

import com.redhat.theses.util.Util

class ThesisController {

    def index() {
        redirect(action: "list", params: params, permanent: true)
    }

    def list(Integer max) {
        params.max = Util.max(max)
        [thesisInstanceList: Thesis.list(params), thesisInstanceTotal: Thesis.count]
    }

    def show(Long id) {
        def thesisInstance = Thesis.get(id)
        if (!thesisInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thesis.label', default: 'Thesis'), id])
            redirect(action: "list")
            return
        }

        [thesisInstance: thesisInstance]
    }
}
