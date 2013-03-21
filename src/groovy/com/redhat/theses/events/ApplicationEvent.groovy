package com.redhat.theses.events

import com.redhat.theses.Application
import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class ApplicationEvent {
    def user
    def application

    ApplicationEvent(Application application, User user) {
        this.application = application
        this.user = user
    }
}
