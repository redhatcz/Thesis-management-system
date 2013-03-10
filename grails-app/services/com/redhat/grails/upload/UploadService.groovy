package com.redhat.grails.upload

import com.redhat.grails.upload.events.UploaderDeleteEvent
import com.redhat.grails.upload.events.UploaderEvent
import org.grails.plugin.platform.events.EventReply
import org.springframework.web.multipart.MultipartFile

class UploadService {

    def springSecurityService

    Map upload(MultipartFile file, String topic = null, Map params = [:]) {
        def response = [success: false, message: null]
        def data = new UploaderEvent(file, params)

        EventReply reply = event(topic: topic, for: 'uploader', data: data, fork: false)
        response = reply.value
        return response
    }

    Map delete(String id, String topic = null, Map params = [:]) {
        def response = [success: false, message: null]
        def data =  new UploaderDeleteEvent(id,  params)

        EventReply reply = event(topic: topic, for: 'uploader-delete', data: data, fork: false)
        response = reply.value
        return response
    }

    private defaultUpload(MultipartFile file, Map params) {
        File destFile = File.createTempFile("tms_${user.id}", null);

        destFile << file.inputStream
    }
}
