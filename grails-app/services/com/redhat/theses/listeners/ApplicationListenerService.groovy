package com.redhat.theses.listeners

import com.redhat.theses.Application
import com.redhat.theses.events.ApplicationApproveddEvent
import grails.events.Listener
import org.springframework.context.ApplicationListener

/**
 * Created with IntelliJ IDEA.
 * User: jcechace
 * Date: 12/27/12
 * Time: 2:16 AM
 * To change this template use File | Settings | File Templates.
 */
class ApplicationListenerService{
    def thesisService

    @Listener(topic = "applicationApproved")
    void approved(Application application) {
        thesisService.createFromApplication(application)
    }
}
