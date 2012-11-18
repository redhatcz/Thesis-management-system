package com.redhat.theses

import com.redhat.theses.auth.User

class FeedService {
    def springSecurityService

    def createFeed(String messageCode, List<String> args = null){
        def feed = new Feed(messageCode: messageCode)
        feed.args = args
        feed.save()
    }

    def createTopicFeed(String messageType, Topic topic) {
        def args = []
        def currentUser = springSecurityService?.currentUser
        if (currentUser instanceof User && currentUser?.fullName) {
            args += [currentUser.fullName, topic.title]
        } else {
            args += [topic.owner.fullName, topic.title]
        }

        def messageCode = "feed.topic.${messageType}"

        createFeed(messageCode, args)
    }

}
