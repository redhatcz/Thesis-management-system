package com.redhat.theses
import com.redhat.theses.events.ArticleEvent
import com.redhat.theses.util.Commons
import org.springframework.transaction.interceptor.TransactionAspectSupport

class TopicService {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    Boolean saveWithSupervisions(Topic topic, List supervisions) {
        String type = topic.id ? 'articleUpdated' : 'articleCreated'
        def removedSupervisions = topic.id ? topic.supervisions.findAll {!(it in supervisions)} : []
        def savedTopic = topic.save(flush: true)
        def success = savedTopic &&
                supervisions.every { it.save() } &&
                removedSupervisions.every { Commons.delete(it) }

        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            event(type, new ArticleEvent(savedTopic, springSecurityService.currentUser, [topic.owner]))
        }
        success
    }


    Boolean deleteWithSupervisions(Topic topic) {
        def subscriptions = Subscription.findAllByArticle(topic)
        def success = topic?.supervisions?.every { Commons.delete(it) } &&
                Comment.findAllByArticle(topic).every { Commons.delete(it) } &&
                subscriptions.every { Commons.delete(it) } &&
                Commons.delete(topic)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            event('articleDeleted', new ArticleEvent(topic, springSecurityService.currentUser, subscriptions*.subscriber))
        }
        success
    }
}
