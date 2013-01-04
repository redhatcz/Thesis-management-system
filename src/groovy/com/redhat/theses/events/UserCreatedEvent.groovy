package com.redhat.theses.events

import com.redhat.theses.auth.User

class UserCreatedEvent {
    def user
    def password

    UserCreatedEvent(User user, String password) {
        this.user = user
        this.password = password
    }
}