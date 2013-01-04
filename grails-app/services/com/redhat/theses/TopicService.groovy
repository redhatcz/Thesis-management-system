package com.redhat.theses

import com.redhat.theses.events.TopicEvent
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class TopicService {

    def supervisionService

    def springSecurityService

    def Boolean saveWithSupervision(Topic topic, List memberships) {
        String type = topic.id ? 'update' : 'insert'
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it.membership in memberships)} : []

        def success = topic.save(flush: true) &&
                supervisionService.saveMany(topic, memberships).every() &&
                removedSupervisions.every { delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            if (type == 'update') {
                event('topicUpdated', 
                    new TopicEvent(topic, springSecurityService.currentUser))
            } else if (type == 'insert') {
                event('topicCreated', 
                    new TopicEvent(topic, springSecurityService.currentUser))
            }

        }
        success
    }

    def Boolean saveSupervisions(Topic topic, List memberships) {
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it.membership in memberships)} : []

        def success = supervisionService.saveMany(topic, memberships).every() &&
                removedSupervisions.every { delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    def Boolean deleteWithSupervisions(Topic topic) {
        def success = topic?.supervisions?.every { delete(it) } &&
                Comment.findAllByArticle(topic).every { delete(it) } &&
                delete(topic)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            event('topicDeleted', 
                new TopicEvent(topic, springSecurityService.currentUser))
        }
        success
    }

    private Boolean delete(entity) {
        def success
        try {
            entity.delete(flush: true)
            success = true
        } catch (DataIntegrityViolationException e) {
            success = false
        }
        success
    }
}
