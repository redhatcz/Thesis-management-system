package com.redhat.theses

import com.redhat.theses.auth.User

class FeedService {
    def grailsLinkGenerator

    def createFeed(String messageCode, List<String> args = null){
        def feed = new Feed(messageCode: messageCode)
        feed.args = args
        feed.save()
    }

    def createTopicFeed(String messageType, Topic topic, currentUser) {
        //If noone is logged in, do not proceed
        if (!currentUser) {
            return
        }
        def user = User.get(currentUser.id)

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
