package com.redhat.theses.events

import com.redhat.theses.Topic
import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class TopicUpdatedEvent extends AuthenticatedUserAwareEvent{
    TopicUpdatedEvent(Topic topic, User currentUser){
        super(topic, currentUser)
    }
}
