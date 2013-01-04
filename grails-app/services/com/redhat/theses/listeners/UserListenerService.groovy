package com.redhat.theses.listeners

import com.redhat.theses.auth.User
import com.redhat.theses.events.UserCreatedEvent
import grails.events.Listener

/**
 * @author vdedik@redhat.com
 */
class UserListenerService {

    @Listener(topic = "userCreated")
    void userCreated(UserCreatedEvent e) {
        println("User with email ${e.user.email} and password ${e.password} created.")
    }
}
