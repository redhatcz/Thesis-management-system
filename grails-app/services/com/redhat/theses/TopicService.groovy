package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class TopicService {

    def supervisionService

    def serviceMethod() {

    }

    def Boolean saveWithSupervision(Topic topic, List memberships) {
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it.membership in memberships)} : []

        def success = topic.save(flush: true) &&
                supervisionService.saveMany(topic, memberships).every() &&
                removedSupervisions.every { delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    def Boolean deleteWithSupervisions(Topic topic){
        def success = topic?.supervisions?.every { delete(it) } && delete(topic)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
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
