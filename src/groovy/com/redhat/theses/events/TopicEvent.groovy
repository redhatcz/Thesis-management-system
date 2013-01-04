package com.redhat.theses.events

import com.redhat.theses.Topic
import com.redhat.theses.auth.User

class TopicEvent {
    def topic
    def user

    TopicEvent(Topic topic, User user) {
        this.topic = topic
        this.user = user
    }
}