package com.redhat.theses

import com.redhat.theses.auth.User
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException

class FeedService {

    /*
     * Dependency injection of LinkGenerator
     */
    def grailsLinkGenerator

    Feed createFeed(String messageCode, List<String> args = null){
        def feed = new Feed(messageCode: messageCode)
        feed.args = args
        feed.save()
    }

    Feed createTopicFeed(Topic topic, String messageType, User user) {
        if (!user) {
            throw new AuthenticationCredentialsNotFoundException(
                    "No user is logged in, topic feed could not be created.")
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
