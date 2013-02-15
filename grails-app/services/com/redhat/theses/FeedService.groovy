package com.redhat.theses

import com.redhat.theses.auth.User

class FeedService {

    /**
     * Dependency injection of org.codehaus.groovy.grails.web.mapping.LinkGenerator
     */
    def grailsLinkGenerator

    Feed createFeed(String messageCode, User user, List<String> args = null){
        def feed = new Feed(messageCode: messageCode, user: user)
        feed.args = args
        feed.save()
    }

    Feed createTopicFeed(Topic topic, String messageType, User user) {
        def args = [
            user.fullName,
            grailsLinkGenerator.link(controller: 'user', action: 'show', id: user.id, absolute: true),
            topic.title
        ]

        if (messageType != 'delete') {
            args += [grailsLinkGenerator.link(controller: 'topic', action: 'show', id: topic.id, absolute: true)]
        }

        def messageCode = "feed.topic.${messageType}"

        createFeed(messageCode, user, args)
    }

    Feed createThesisFeed(Thesis thesis, String messageType, User user) {
        def args = [
            user.fullName,
            grailsLinkGenerator.link(controller: 'user', action: 'show', id: user.id, absolute: true),
            thesis.id
        ]

        if (messageType != 'delete') {
            args += [grailsLinkGenerator.link(controller: 'thesis', action: 'show', id: thesis.id, absolute: true)]
        }

        def messageCode = "feed.thesis.${messageType}"

        createFeed(messageCode, user, args)
    }
}
