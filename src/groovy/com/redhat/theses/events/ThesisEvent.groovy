package com.redhat.theses.events

import com.redhat.theses.Thesis
import com.redhat.theses.auth.User

class ThesisEvent {
    def thesis
    def user

    ThesisEvent(Thesis thesis, User user) {
        this.thesis = thesis
        this.user = user
    }
}