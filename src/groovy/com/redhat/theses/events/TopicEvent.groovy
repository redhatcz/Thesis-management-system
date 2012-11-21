package com.redhat.theses.events

import com.redhat.theses.Topic
import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class TopicEvent extends AuthenticatedUserAwareEvent {
    String type

    TopicEvent(Topic topic, User currentUser, type){
        super(topic, currentUser)
        this.type = type
    }
}
