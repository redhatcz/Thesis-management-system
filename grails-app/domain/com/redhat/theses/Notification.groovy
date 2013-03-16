package com.redhat.theses

import com.redhat.theses.auth.User

class Notification {

    User user
    Feed feed

    static mapWith = "mongo"

    static mapping = {
        version false
    }
}