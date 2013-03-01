package com.redhat.theses.events

import com.redhat.theses.Topic
import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class TopicUpdatedEvent {
    def oldTopic
    def newTopic
    def user

    TopicUpdatedEvent(Topic oldTopic, Topic newTopic, User user) {
        this.oldTopic = oldTopic
        this.newTopic = newTopic
        this.user = user
    }
}
