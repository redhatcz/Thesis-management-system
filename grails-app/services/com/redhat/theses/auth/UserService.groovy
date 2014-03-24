package com.redhat.theses.auth

import com.redhat.theses.util.Commons
import com.redhat.theses.events.UserCreatedEvent
import org.apache.commons.lang.RandomStringUtils

class UserService {

    def save(User user) {
        String eventType = user.id ? 'update' : 'create'

        def createdByAdmin = false
        String password = user.password
        if (!password) {
            createdByAdmin = true
            password = RandomStringUtils.random(8, true, true)
            user.password = password
        }

        def persistedUser = user.save()

        if (persistedUser && createdByAdmin) { // temporary fix
            if (eventType == 'create') {
                event('userCreated',
                    new UserCreatedEvent(persistedUser, password, createdByAdmin))
            }
        }
        persistedUser
    }

    def delete(User user) {
        Commons.delete(user)
    }
}
