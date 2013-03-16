package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
abstract class Article {

    String title

    static constraints = {
        title nullable: false, blank: false
    }

    static mapping = {
        tablePerHierarchy false
        id generator: 'sequence', params: [sequence: 'seq_article_id']
    }
}
