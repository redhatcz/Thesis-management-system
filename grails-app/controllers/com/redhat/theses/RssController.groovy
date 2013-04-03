package com.redhat.theses

import com.redhat.theses.util.Util

class RssController {

    static defaultAction = "feed"

    static final Long MAX_RSS_FEEDS

    def feed() {

        if (Util.isActionInUrl(request, 'feed')) {
            redirect uri: '/rss', permanent: true
        }

        def topicList = Topic.findAllByEnabled(true, [sort: 'dateCreated', order: 'desc', max: MAX_RSS_FEEDS])
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
