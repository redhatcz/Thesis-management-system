package com.redhat.grails.upload
import grails.converters.JSON
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

class UploadController {

    def uploadService
    def springSecurityService
    def gridFileService

    def upload() {
        try {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile('qqfile')
            String topic = params?.topic

            def result = uploadService.upload(file, topic, params)

            return render(text: result as JSON, contentType: 'text/json')
        } catch (Exception e) {
            log.error('Error during file upload', e)
            def errorMessage = message(code: 'uploader.error.upload')
            return render(text: [success: false, message: errorMessage] as JSON,
                    contentType: 'text/json')
        }
    }
}
