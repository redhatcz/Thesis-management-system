package com.redhat.theses.events

import com.redhat.theses.RoleRequest
import com.redhat.theses.auth.User


class RoleRequestEvent {
    def user
    def request

    RoleRequestEvent(RoleRequest request, User user) {
        this.request = request
        this.user = user
    }
}
