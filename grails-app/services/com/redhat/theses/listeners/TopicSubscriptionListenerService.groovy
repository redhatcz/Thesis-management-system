package com.redhat.theses.listeners

import org.springframework.context.ApplicationListener
import com.redhat.theses.events.TopicEvent
import com.redhat.theses.Subscription
import org.springframework.context.i18n.LocaleContextHolder as LCH

/**
 * @author vdedik@redhat.com
 */
class TopicSubscriptionListenerService implements ApplicationListener<TopicEvent> {

    def messageSource

    def grailsLinkGenerator

    @Override
    void onApplicationEvent(TopicEvent e) {
        //TODO: do not send email to the currentUser
        Subscription.findAllByArticle(e.source).each {
            //TODO: send email instead of printing the message to stdout, and also refactoring needed
            println "Sending mail to subscriber ${it.subscriber.fullName}:"
            def args = [
                e.currentUser.fullName,
                grailsLinkGenerator.link(controller: 'user', action: 'show', id: e.currentUser.id),
                e.source.title
            ]

            if (e.type != 'delete') {
                args += [grailsLinkGenerator.link(controller: 'topic', action: 'show', id: e.source.id)]
            }

            println messageSource.getMessage("feed.topic.${e.type}", args.toArray(), LCH.getLocale())
        }
    }
}
