package com.redhat.theses.events

import org.springframework.context.ApplicationEvent
import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
abstract class AuthenticatedUserAwareEvent extends ApplicationEvent {
    User currentUser

    AuthenticatedUserAwareEvent(Object source, User currentUser) {
        super(source)
        this.currentUser = currentUser
    }
}
