package com.redhat.theses.events

import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class EmailChangedEvent {
    def user
    def email

    EmailChangedEvent(User user, String email) {
        this.user = user
        this.email = email
    }
}
