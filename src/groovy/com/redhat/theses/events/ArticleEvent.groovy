package com.redhat.theses.events

import com.redhat.theses.Article
import com.redhat.theses.auth.User

class ArticleEvent {
    def article
    def user
    def extraSubscribers

    ArticleEvent(Article article, User user, List<User> extraSubscribers = []) {
        this.article = article
        this.user = user
        this.extraSubscribers = extraSubscribers
    }
}