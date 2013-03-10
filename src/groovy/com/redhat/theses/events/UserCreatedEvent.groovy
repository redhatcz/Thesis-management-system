package com.redhat.theses.events

import com.redhat.theses.auth.User

class UserCreatedEvent {
    def user
    def password
    def createdByAdmin

    UserCreatedEvent(User user, String password, Boolean createdByAdmin = false) {
        this.user = user
        this.password = password
        this.createdByAdmin = createdByAdmin
    }
}