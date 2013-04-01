package com.redhat.theses.listeners
import com.redhat.theses.Thesis
import com.redhat.theses.auth.User
import com.redhat.theses.util.UploadUtil
import grails.events.Listener
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils as SSU
import org.imgscalr.Scalr
import org.springframework.context.i18n.LocaleContextHolder as LCH

import java.awt.image.BufferedImage

class UploadListenerService {

    def springSecurityService
    def gridFileService
    def messageSource
    def gspTagLibraryLookup

    /**
     * Handles upload of user avatar. On success this methods will return JSON response containing
     * bucket, group and id of user's main avatar together with success property set to true
     *
     * @param event
     * @return
     */
     @Listener(topic = "avatar", namespace = 'uploader')
     Map avatar(event) {
        def response = [success: false, message: null]
        def user = User.get(event?.params?.id)
        def currentUser = springSecurityService.currentUser

        if (!user) {
            response.message = messageSource.getMessage('user.not.found', [id].toArray(), LCH.locale)
            return response
        }

        if (user != currentUser && SSU.ifNotGranted('ROLE_ADMIN')) {
            response.message = messageSource.getMessage('security.denied.message', [].toArray(),
                    LCH.locale)
            return response
        }

        // Convert MultipartFile into BufferedImage
        BufferedImage image = UploadUtil.toImage(event?.file)

        if (!image) {
            response.message = messageSource.getMessage('file.image.type.error',[].toArray(),
                    LCH.locale)
            return response
        }

        if (image.width != image.height) {
            response.message = messageSource.getMessage('avatar.aspectRatio.error',[].toArray(),
                    LCH.locale)
            return response
        }
        // Resize it to
        def avatar =  Scalr.resize(image, 296)
        def smallAvatar = Scalr.resize(image, 36)

        // Free the resources
        image.flush()

        def saved = gridFileService.save(content: UploadUtil.toBytes(avatar),
                                         object: user,
                                         filename: 'avatar.png',
                                         group: 'avatar',
                                         delete: true)

        def saved2 = gridFileService.save(content: UploadUtil.toBytes(smallAvatar),
                                          object: user,
                                          filename: 'avatar_small.png',
                                          group: 'avatar_small',
                                          delete: true)

        if (saved && saved2) {
            response.id = saved.id.toString()
            response.group = saved?.metaData?.group
            response.bucket = User.bucketMapping
            response.success = true
        } else {
            if (saved) {
                gridFileService.deleteFileByRawMongoId(saved.id , User.bucketMapping)
            }
            if (saved2) {
                gridFileService.deleteFileByRawMongoId(saved2.id , User.bucketMapping)
            }
        }


        return response
    }

    def @Listener(topic = "avatar", namespace = 'uploader-delete')
    Map deleteAvatar(event) {
        def response = [success: false, message: null]
        def user = User.get(event?.id)
        def currentUser = springSecurityService.currentUser

        if (!user) {
            response.message = messageSource.getMessage('user.not.found', [id].toArray(), LCH.locale)
            return response
        }

        if (user != currentUser && SSU.ifNotGranted('ROLE_ADMIN')) {
            response.message = messageSource.getMessage('security.denied.message', [].toArray(),
                    LCH.locale)
            return response
        }

        def before = gridFileService.getBoundFile(user, 'id', 'avatar')
        def beforeSmall = gridFileService.getBoundFile(user, 'id', 'avatar_small')

        if (before || beforeSmall) {
            gridFileService.deleteFileByRawMongoId(before.id, User.bucketMapping)
            gridFileService.deleteFileByRawMongoId(beforeSmall.id, User.bucketMapping)

            def after = gridFileService.getBoundFile(user, 'id', 'avatar')
            def afterSmall = gridFileService.getBoundFile(user, 'id', 'avatar_small')

            // true if file existed and was deleted, false otherwise
            if (after && afterSmall) {
                response.message = messageSource.getMessage('uploader.error.delete', [].toArray(),
                        LCH.locale)
            } else {
                response.success = true
                response.message = messageSource.getMessage('avatar.delete.message',[].toArray(),
                        LCH.locale)
            }
        } else {
            response.message = messageSource.getMessage('uploader.error.not.found', [].toArray(),
                    LCH.locale)
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

        def saved = gridFileService.save(content: event.file, object: thesis)
        if (saved) {
            def g = gspTagLibraryLookup.lookupNamespaceDispatcher('g')
            response.id = saved.id.toString()
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
