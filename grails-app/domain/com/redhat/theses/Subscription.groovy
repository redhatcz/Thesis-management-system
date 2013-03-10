package com.redhat.theses

import com.redhat.theses.auth.User

class Subscription {

    User subscriber
    Article article

    static mapping = {
        version false
    }
}
