package com.redhat.theses.auth

/**
 * @author vdedik@redhat.com
 */
class EmailConfirmationController {

    def linkExpired() {
        render view: '/emailConfirmation/lifecycle', model: [
                redirect: false,
                title: message(code: 'link.expired.title'),
                header: message(code: 'link.expired.header'),
                content: message(code: 'link.expired.message')
        ]
    }
}
