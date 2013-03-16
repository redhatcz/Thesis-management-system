package com.redhat.theses.events

import com.redhat.theses.Comment

/**
 * @author vdedik@redhat.com
 */
class CommentEvent {

    def comment

    CommentEvent(Comment comment) {
        this.comment = comment
    }
}
