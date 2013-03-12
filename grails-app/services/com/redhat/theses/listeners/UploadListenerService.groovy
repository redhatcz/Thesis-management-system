package com.redhat.theses.listeners

import com.redhat.theses.Thesis
import grails.events.Listener
import org.springframework.context.i18n.LocaleContextHolder as LCH

class UploadListenerService {

    def springSecurityService
    def gridFileService
    def messageSource

    @Listener(topic = "avatar", namespace = 'uploader')
    Map avatar(event) {
        def response = [success: false, message: null]
        def user = springSecurityService.currentUser

        if (user) {
            def saved = gridFileService.save(file: event.file, object: user, group: 'avatar')
            // true if file was saved
            response.success = saved || false
        }

        return response
    }

    @Listener(topic = "thesis", namespace = 'uploader')
    Map thesis(event) {
        def response = [success: false, message: null]
        def id = event.params.id
        def thesis = Thesis.get(id)
        def user = springSecurityService.currentUser

        if (!thesis) {
            response.message = messageSource.getMessage('thesis.not.found', [id].toArray(), LCH.locale)
            return response
        }

        if (thesis.assigneeId != user.id) {
            response.message = messageSource.getMessage('security.denied.message', [].toArray(),
                    LCH.locale)
            return response
        }

        def saved = gridFileService.save(file: event.file, object: thesis)
        // true if file was saved
        response.success = saved || false

        return response
    }

    @Listener(topic = "thesis", namespace = 'uploader-delete')
    Map deleteThesis(event) {
        def response = [success: false, message: null]
        println event.params
        def thesisId = event.params.thesisId
        def thesis = Thesis.get(thesisId)
        def user = springSecurityService.currentUser

        if (!thesis) {
            response.message = messageSource.getMessage('thesis.not.found', [thesisId].toArray(), LCH.locale)
            return response
        }

        if (thesis.assigneeId != user.id) {
            response.message = messageSource.getMessage('security.denied.message', [].toArray(),
                    LCH.locale)
            return response
        }

        def existsBefore = gridFileService.getFileByMongoId(event.id, Thesis.bucketMapping)

        if (existsBefore) {
            gridFileService.deleteFileByMongoId(event.id, Thesis.bucketMapping)
        } else {
            response.message =  messageSource.getMessage('uploader.error.not_found', [].toArray(),
                    LCH.locale)
            def existsAfter = gridFileService.getFileByMongoId(event.id, Thesis.bucketMapping)
            // true if file existed and was deleted, false otherwise
            response.success = existsBefore && !existsAfter
        }

        return response
    }
}
