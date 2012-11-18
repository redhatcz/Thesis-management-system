package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
abstract class Article {

    static mapping = {
        tablePerHierarchy false
        id generator: 'sequence', params: [sequence: 'seq_article_id']
    }

    def beforeDelete() {
        Comment.withNewTransaction {
            def comments = Comment.findAllByArticle(this);
            comments.each {
                it.delete()
            }
        }
    }
}
