package com.redhat.theses

import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class Comment {
    String content
    Date dateCreated
    User user
    Article article
    boolean privateComment = false

    static constraints = {
        content widget: 'textarea', nullable: false, blank: false
        article nullable: false
        user nullable: false
    }

    static mapping = {
        content type: 'text'
        sort 'dateCreated'
    }

    String toString() {
        content
    }
}
