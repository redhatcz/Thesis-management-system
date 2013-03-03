package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class CommentService {

    Map<Article, Integer> countByArticles(List<Article> articles) {
        if (!articles || articles.size() == 0) {
            []
        } else {
            Comment.createCriteria().list {
                projections {
                    groupProperty 'article'
                    countDistinct 'id'
                }
                'in' 'article', articles
            }.collectEntries { [it[0], it[1]] }
        }
    }
}
