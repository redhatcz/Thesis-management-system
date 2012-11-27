package com.redhat.theses.listeners

import com.redhat.theses.events.TopicCreatedEvent
import org.springframework.context.ApplicationListener
import com.redhat.theses.Topic

/**
 * @author vdedik@redhat.com
 */
class TopicCreatedListenerService implements ApplicationListener<TopicCreatedEvent> {

    def feedService

    @Override
    void onApplicationEvent(TopicCreatedEvent e) {
        feedService.createTopicFeed((Topic) e.source, 'insert', e.currentUser)
    }
}
