package com.redhat.theses.events

import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class UserConfirmedEvent {
    User user
    User admin

    UserConfirmedEvent(User user, User admin) {
        this.user = user
        this.admin = admin
    }
}
