package com.redhat.grails.upload

import com.redhat.grails.upload.events.UploaderEvent
import com.redhat.grails.upload.exceptions.FileUploadException
import org.springframework.web.multipart.MultipartFile

// Todo error handling from events
class UploadService {

    def gridFileService
    def springSecurityService

    def upload(MultipartFile file, String topic = null, Map params = []) {
        try {
            def data = new UploaderEvent(file, params)

            def reply = event(topic: 'thesis', for: 'uploader', data: data, fork: false).waitFor()

            if (reply.hasErrors()) {
                throw new FileUploadException(reply.getErrors()[1])
            }
            return true
        } catch (Exception e) {
            log.error('Error during file upload', e)
            throw new FileUploadException(e)
        }
        return false
    }

    private defaultUpload(MultipartFile file, Map params) {
        File destFile = File.createTempFile("tms_${user.id}", null);

        destFile << file.inputStream
    }
}
