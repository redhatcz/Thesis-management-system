package com.redhat.theses

import com.redhat.theses.events.TopicEvent
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class TopicService {

    /*
     * Dependency injection of SupervisionService
     */
    def supervisionService

    /*
     * Dependency injection of SpringSecurityService
     */
    def springSecurityService

    Boolean saveWithSupervision(Topic topic, List memberships) {
        String type = topic.id ? 'topicUpdated' : 'topicCreated'
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it.membership in memberships)} : []

        def success = topic.save(flush: true) &&
                supervisionService.saveMany(topic, memberships).every() &&
                removedSupervisions.every { delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            event(type, new TopicEvent(topic, springSecurityService.currentUser))
        }
        success
    }

    Boolean saveSupervisions(Topic topic, List memberships) {
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it.membership in memberships)} : []

        def success = supervisionService.saveMany(topic, memberships).every() &&
                removedSupervisions.every { delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    Boolean deleteWithSupervisions(Topic topic) {
        def success = topic?.supervisions?.every { delete(it) } &&
                Comment.findAllByArticle(topic).every { delete(it) } &&
                delete(topic)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            event('topicDeleted', new TopicEvent(topic, springSecurityService.currentUser))
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
