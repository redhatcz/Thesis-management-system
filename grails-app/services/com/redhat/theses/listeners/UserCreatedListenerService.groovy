package com.redhat.theses.listeners

import org.springframework.context.ApplicationListener
import com.redhat.theses.events.UserCreatedEvent

/**
 * @author vdedik@redhat.com
 */
class UserCreatedListenerService implements ApplicationListener<UserCreatedEvent> {

    //TODO: send mail instead of println
    @Override
    void onApplicationEvent(UserCreatedEvent e) {
        println("User with email ${e.source.email} and password ${e.password} created.")
    }
}
