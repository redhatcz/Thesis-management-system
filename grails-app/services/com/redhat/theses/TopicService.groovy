package com.redhat.theses

import com.redhat.theses.events.TopicEvent
import com.redhat.theses.util.Commons
import org.springframework.transaction.interceptor.TransactionAspectSupport

class TopicService {

    /**
     * Dependency injection of com.redhat.theses.SupervisionService
     */
    def supervisionService

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    Boolean saveWithSupervision(Topic topic, List memberships) {
        String type = topic.id ? 'topicUpdated' : 'topicCreated'
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it.membership in memberships)} : []

        def success = topic.save(flush: true) &&
                supervisionService.saveMany(topic, memberships).every() &&
                removedSupervisions.every { Commons.delete(it) }

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
                removedSupervisions.every { Commons.delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
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
