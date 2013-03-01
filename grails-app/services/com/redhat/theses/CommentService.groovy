package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class CommentService {

    Map<Article, Integer> countByArticles(List<Article> articles) {
        return Comment.createCriteria().list {
            projections {
                groupProperty 'article'
                countDistinct 'id'
            }
            'in' 'article', articles
        }.collectEntries { [it[0], it[1]] }
    }
}
