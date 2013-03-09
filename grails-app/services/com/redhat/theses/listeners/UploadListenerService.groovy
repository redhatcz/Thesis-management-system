package com.redhat.theses.listeners
import com.redhat.theses.Thesis
import com.redhat.theses.auth.User
import grails.events.Listener

// TODO: error messages when its integrated in uploader plugin -- for now we return null
class UploadListenerService {

    def springSecurityService
    def gridFileService

    @Listener(topic = "avatar", namespace = 'uploader')
    boolean avatar( event) {
        def user = springSecurityService.currentUser
        if (!user) {
            return null
        }

        gridFileService.save(file: event.file, object: user, group: 'avatar')
    }

    @Listener(topic = "thesis" ,namespace = 'uploader')
    def thesis( event) {
        def thesis = Thesis.get(event.params.id)
        User user = springSecurityService.currentUser

        if (!thesis){
            return null
        }

        if (thesis.assigneeId != user.id) {
            return null
        }

        gridFileService.save(file: event.file, object: thesis)
    }
}
