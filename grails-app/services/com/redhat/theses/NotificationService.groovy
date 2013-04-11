package com.redhat.theses

import com.redhat.theses.auth.User

/**
 * @author vdedik@redhat.com
 */
class NotificationService {

    /**
     * Dismisses all notifications of a user
     */
    def dismissNotificationsByUser(User user) {
        def notifications = Notification.findAllByUserAndSeen(user, false)

        notifications.every {
            it.seen = true
            it.save()
        }
    }
}
