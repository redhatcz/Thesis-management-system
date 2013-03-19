package com.redhat.theses

class RssController {

    def feed() {

        def topicList = Topic.findAll(max: 20, sort: 'dateCreated', order: 'desc')
        render(feedType: 'rss') {
            title = message(code: 'rss.main.feed.title')
            link = createLink(controller: 'rss', action: 'feed', absolute: true)
            description = message(code: 'rss.main.feed.description')
            topicList.each { topic ->
                entry(topic.title) {
                    title = topic.title
                    link = createLink(controller: 'topic', action: 'show', id: topic.id, absolute: true)
                    publishedDate =  topic.dateCreated
                    topic.lead
                }
            }
        }
    }
}
