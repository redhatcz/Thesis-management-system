package com.redhat.theses

import com.redhat.theses.auth.User

class FeedService {

    Feed createFeed(String messageCode, User user, List<String> args = null){
        def feed = new Feed(messageCode: messageCode, user: user)
        feed.args = args
        feed.save()
    }
}
