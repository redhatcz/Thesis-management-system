package com.redhat.theses.listeners

import com.redhat.theses.Thesis
import grails.events.Listener
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils as SSU
import org.springframework.context.i18n.LocaleContextHolder as LCH

class UploadListenerService {

    def springSecurityService
    def gridFileService
    def messageSource
    def gspTagLibraryLookup

    def @Listener(topic = "avatar", namespace = 'uploader')
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

        // if user is not asignee
        if (thesis.assigneeId != user.id) {
            // check if he is topic owner, thesis supervisor or admin
            def isAdmin = SSU.ifAllGranted('ROLE_ADMIN')
            def isSupervisor = thesis.supervisorId == user.id
            def isOwner = thesis.topic.ownerId == user.id

            // if he isn't then return response
            if (!isAdmin && !isSupervisor && !isOwner) {
                response.message = messageSource.getMessage('security.denied.message', [].toArray(),
                        LCH.locale)
                return response
            }
        }

        def saved = gridFileService.save(file: event.file, object: thesis)
        if (saved) {
            def g = gspTagLibraryLookup.lookupNamespaceDispatcher('g')
            response.id = saved.id.toString()
            response.filename = saved.filename
            response.type = saved.contentType
            // This needs to be formatted -- don't know how yet.
            response.date = g.formatDate(date: saved.uploadDate, dateStyle: 'LONG',
                    type: 'datetime')
            response.bucket = Thesis.bucketMapping
            response.success = true
        }

        return response
    }

    @Listener(topic = "thesis", namespace = 'uploader-delete')
    Map deleteThesis(event) {
        def response = [success: false, message: null]
        def thesisId = event.params.thesisId
        def thesis = Thesis.get(thesisId)
        def user = springSecurityService.currentUser


        if (!thesis) {
            response.message = messageSource.getMessage('thesis.not.found', [thesisId].toArray(), LCH.locale)
            return response
        }

        // if user is not asignee
        if (thesis.assigneeId != user.id) {
            // check if he is topic owner, thesis supervisor or admin
            def isAdmin = SSU.ifAllGranted('ROLE_ADMIN')
            def isSupervisor = thesis.supervisorId == user.id
            def isOwner = thesis.topic.ownerId == user.id

            // if he isn't then return response
            if (!isAdmin && !isSupervisor && !isOwner) {
                response.message = messageSource.getMessage('security.denied.message', [].toArray(),
                        LCH.locale)
                return response
            }
        }


        def before = gridFileService.getFileByMongoId(event.id, Thesis.bucketMapping)

        if (before) {
            gridFileService.deleteFileByMongoId(event.id, Thesis.bucketMapping)
            def after = gridFileService.getFileByMongoId(event.id, Thesis.bucketMapping)
            // true if file existed and was deleted, false otherwise
            if (after) {
                response.message = messageSource.getMessage('uploader.error.delete', [].toArray(),
                        LCH.locale)
            } else {
                response.success = true
                response.id = before.id.toString()
                response.name = before.filename
            }
        } else {
            response.message = messageSource.getMessage('uploader.error.not.found', [].toArray(),
                    LCH.locale)
        }

        return response
    }
}
