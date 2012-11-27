package com.redhat.theses

import com.redhat.theses.auth.User

class FeedService {
    def grailsLinkGenerator

    Feed createFeed(String messageCode, List<String> args = null){
        def feed = new Feed(messageCode: messageCode)
        feed.args = args
        feed.save()
    }

    Feed createTopicFeed(Topic topic, String messageType, User user) {
        //If noone is logged in, do not proceed
        //TODO: remove when security is done
        if (!user) {
            return null
        }

        def args = [
            user.fullName,
            grailsLinkGenerator.link(controller: 'user', action: 'show', id: user.id),
            topic.title
        ]

        if (messageType != 'delete') {
            args += [grailsLinkGenerator.link(controller: 'topic', action: 'show', id: topic.id)]
        }

        def messageCode = "feed.topic.${messageType}"

        createFeed(messageCode, args)
    }

}
