package com.redhat.theses.listeners
import com.redhat.theses.Thesis
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

class UploadListenerService {

    def springSecurityService
    def gridFileService
    def messageSource

    @Listener(topic = "avatar", namespace = 'uploader')
    boolean avatar( event) {
        def response = [success: false, message: null]
        def user = springSecurityService.currentUser

        if (user) {
            def saved = gridFileService.save(file: event.file, object: user, group: 'avatar')
            // true if file was saved
            response.success = saved || false
        }

        return  response
    }

    @Listener(topic = "thesis" ,namespace = 'uploader')
    def thesis( event) {
        def response = [success: false, message: null]
        def id = event.params.id
        def thesis = Thesis.get(id)
        def user = springSecurityService.currentUser

        if (!thesis){
            response.message = messageSource.getMessage('thesis.not.found', [id].toArray(), LCH.locale)
            return response
        }

        if (thesis.assigneeId != user.id) {
            response.message = messageSource.getMessage('action.permission.denied',[].toArray(),
                    LCH.locale )
            return response
        }

        def saved = gridFileService.save(file: event.file, object: thesis)
        // true if file was saved
        response.success = saved || false

        return response
    }
}
