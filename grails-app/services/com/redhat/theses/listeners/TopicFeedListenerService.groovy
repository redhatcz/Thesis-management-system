package com.redhat.theses.listeners

import org.springframework.context.ApplicationListener
import com.redhat.theses.events.TopicEvent
import com.redhat.theses.Topic

/**
 * @author vdedik@redhat.com
 */
class TopicFeedListenerService implements ApplicationListener<TopicEvent> {
    def feedService

    @Override
    void onApplicationEvent(TopicEvent e) {
        feedService.createTopicFeed((Topic) e.source, e.type, e.currentUser)
    }
}
