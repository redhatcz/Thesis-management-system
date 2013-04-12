package com.redhat.theses

import com.redhat.theses.util.Util
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

/**
 * @author vdedik@redhat.com
 */
@Secured(['isAuthenticated()'])
class NotificationController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.NotificationService
     */
    def notificationService

    static defaultAction = "list"

    def list() {
        def user = springSecurityService.currentUser

        params.max = Util.max(params.max)
        params.sort = 'dateCreated'
        params.order = 'desc'

        def notificationList = Notification.findAllByUser(user, params)
        def notificationListTotal = Notification.countByUser(user)

        [notificationList: notificationList, notificationListTotal: notificationListTotal]
    }

    def dismiss() {
        def user = springSecurityService.currentUser

        def notificationList = Notification.findAllByUser(user, params)
        def notificationListTotal = Notification.countByUser(user)
        if (!notificationService.dismissNotificationsByUser(springSecurityService.currentUser)) {
            flash.message = message code: 'notification.not.dismissed'
            render view: 'list', model:
                    [notificationList: notificationList, notificationListTotal: notificationListTotal]
            return
        }

        flash.message = message code: 'notification.dismissed'
        redirect action: 'list'
    }

    def dismissAjax() {
        if (notificationService.dismissNotificationsByUser(springSecurityService.currentUser)) {
            render([success: true] as JSON)
        } else {
            render([success: false] as JSON)
        }
    }
}
