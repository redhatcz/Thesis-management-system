package com.redhat.theses.events

import org.springframework.context.ApplicationEvent
import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class UserCreatedEvent extends ApplicationEvent {
    String password
    UserCreatedEvent(User user, String password) {
        super(user)
        this.password = password
    }
}
