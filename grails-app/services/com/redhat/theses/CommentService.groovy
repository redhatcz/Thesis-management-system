package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class CommentService {

    /**
     * Counts comments by articles
     *
     * @param publicOnly - if to count only public comments
     * @return number of comments
     */
    Map<Article, Integer> countByArticles(List<Article> articles, publicOnly = true) {
        def result = [:]
        if (articles && articles.size() != 0) {
            result = Comment.createCriteria().list {
                projections {
                    groupProperty 'article'
                    countDistinct 'id'
                }
                'in' 'article', articles
                if (publicOnly) {
                    eq 'privateComment', false
                }
            }.collectEntries { [it[0], it[1]] }
        }
        result
    }
}
