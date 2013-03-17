package com.redhat.theses.ajax

import grails.converters.JSON

/**
 * @author vdedik@redhat.com
 */
class NotificationController {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
    def springSecurityService

    /**
     * Dependency injection of com.redhat.theses.NotificationService
     */
    def notificationService

    def dismissNotifications() {
        if (notificationService.dismissNotificationsByUser(springSecurityService.currentUser)) {
            render([success: true] as JSON)
        } else {
            render([success: false] as JSON)
        }
    }
}
