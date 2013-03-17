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

    def render = { attrs, body ->
        def notificationCount = Notification.countByUserAndSeen(springSecurityService.currentUser, false)
        def notifications = Notification.findAllByUser(springSecurityService.currentUser,
                [max: Math.max(notificationCount, MAX_SHOWN_NOTIFICATIONS), sort: 'dateCreated', order: 'desc'])

        def popoverTitle = g.message(code: "notification.${notificationCount ? 'new' : 'no.new'}.title")
        def popoverContent = ''

        if (!notifications || notifications.empty) {
            popoverContent = "<div>${g.message(code: 'notification.no.notifications').toString()}</div>"
        } else {
            notifications.each {
                def message = g.message(code: it.feed.messageCode, args: it.feed.args)
                popoverContent += g.render(template: '/taglib/notification/notification',
                        model: [isNew: !it.seen, message: message])
            }
        }

        popoverContent = popoverContent.replaceAll('\n', '').replaceAll("\\'", "\\\\'")

        out << g.render(template: "/taglib/notification/notifications",
                model: [notificationCount: notificationCount, popoverTitle: popoverTitle, popoverContent: popoverContent])
    }
}
