package com.redhat.theses.events

import org.springframework.context.ApplicationEvent
import com.redhat.theses.Topic

/**
 * @author vdedik@redhat.com
 */
class TopicEvent extends ApplicationEvent {
    String type
    def currentUser

    TopicEvent(String type, Topic topic, currentUser){
        super(topic)
        this.type = type
        this.currentUser = currentUser
    }
}
