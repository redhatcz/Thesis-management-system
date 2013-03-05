package com.redhat.theses.listeners
import com.redhat.theses.Thesis
import grails.events.Listener
// Todo: security and error handling
class UploadListenerService {

    def springSecurityService
    def gridFileService

    @Listener(topic = "avatar", namespace = 'uploader')
    boolean avatar( event) {
        def user = springSecurityService.currentUser
        gridFileService.save(file: event.file, object: user, group: 'avatar')
    }

    @Listener(topic = "thesis" ,namespace = 'uploader')
    def thesis( event) {
        def thesis = Thesis.get(14)
        if (!thesis){
            return null
        }
        // todo Secure uploadTo

        gridFileService.save(file: event.file, object: thesis)
    }
}
