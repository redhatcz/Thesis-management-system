package com.redhat.theses
import com.redhat.theses.events.TopicEvent
import com.redhat.theses.events.TopicUpdatedEvent
import com.redhat.theses.util.Commons
import org.springframework.transaction.interceptor.TransactionAspectSupport

class TopicService {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    Boolean saveWithSupervisions(Topic topic, List supervisions) {
        String type = topic.id ? 'topicUpdated' : 'topicCreated'
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it in supervisions)} : []
        def savedTopic = topic.save(flush: true)
        def success = savedTopic &&
                supervisions.every { it.save(failOnError: true) } &&
                removedSupervisions.every { Commons.delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            if (type == 'topicUpdated') {
                event(type, new TopicUpdatedEvent(topic, savedTopic, springSecurityService.currentUser))
            } else {
                event(type, new TopicEvent(savedTopic, springSecurityService.currentUser))
            }
        }
        success
    }


    Boolean deleteWithSupervisions(Topic topic) {
        def success = topic?.supervisions?.every { Commons.delete(it) } &&
                Comment.findAllByArticle(topic).every { Commons.delete(it) } &&
                Commons.delete(topic)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            event('topicDeleted', new TopicEvent(topic, springSecurityService.currentUser))
        }
        success
    }
}
