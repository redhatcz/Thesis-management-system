package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
abstract class Article {

    static mapping = {
        tablePerHierarchy false
        id generator: 'sequence', params: [sequence: 'seq_article_id']
    }
}
