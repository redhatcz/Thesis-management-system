package com.redhat.theses.listeners

import com.redhat.theses.events.TopicDeletedEvent
import org.springframework.context.ApplicationListener
import com.redhat.theses.Topic

/**
 * @author vdedik@redhat.com
 */
class TopicDeletedListenerService implements ApplicationListener<TopicDeletedEvent> {

    def feedService

    @Override
    void onApplicationEvent(TopicDeletedEvent e) {
        feedService.createTopicFeed((Topic) e.source, 'delete', e.currentUser)
    }
}
