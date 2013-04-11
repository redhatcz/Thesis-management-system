package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class NotificationTagLib {
    static namespace = "notification"

    static MAX_SHOWN_NOTIFICATIONS = 5

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Renders notifications
     */
    def render = { attrs, body ->
        def notificationCount = Notification.countByUserAndSeen(springSecurityService.currentUser, false)
        def notifications = Notification.findAllByUser(springSecurityService.currentUser,
                [max: MAX_SHOWN_NOTIFICATIONS, sort: 'dateCreated', order: 'desc'])

        def popoverContent = g.render(template: '/taglib/notification/notificationList',
                model: [notifications: notifications, showMoreButton: true])
                              .replaceAll('\n', '').replaceAll("\\'", "\\\\'")

        out << g.render(template: "/taglib/notification/notifications",
                model: [notificationCount: notificationCount, popoverContent: popoverContent])
    }
}
