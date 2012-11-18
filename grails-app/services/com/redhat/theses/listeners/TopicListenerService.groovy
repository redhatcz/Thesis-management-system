package com.redhat.theses.listeners

import org.springframework.context.ApplicationListener
import com.redhat.theses.events.TopicEvent
import com.redhat.theses.Topic

/**
 * @author vdedik@redhat.com
 */
class TopicListenerService implements ApplicationListener<TopicEvent> {
    def feedService

    @Override
    void onApplicationEvent(TopicEvent e) {
        feedService.createTopicFeed(e.type, (Topic) e.source)
    }
}
