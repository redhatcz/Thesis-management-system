package com.redhat.theses.events

import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class LostPasswordEvent {
    User user

    LostPasswordEvent(User user) {
        this.user = user
    }
}
