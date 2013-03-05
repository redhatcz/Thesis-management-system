package com.redhat.grails.upload

import grails.converters.JSON
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpServletRequest

class UploadController {

    def uploadService
    def springSecurityService
    def gridFileService

    def upload() {
        try {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile('qqfile')
            String callback = params?.callback ?: 'avatar'

            Boolean success = uploadService.upload(file, callback, params)

            return render(text: [success: success] as JSON, contentType: 'text/json')
        } catch (Exception e) {
            log.error('Error during file upload', e)
            def errorMessage = message(code: 'uploader.error.upload', default: 'An error has occurred during the file upload')
            return render(text: [success: false, message: errorMessage] as JSON, contentType: 'text/json')
        }
    }




    private InputStream getInputStream(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {

            return uploaded.inputStream
        }
        request.inputStream
    }


    def file() {
        def user = springSecurityService.currentUser

        def file = gridFileService.getFile(user, 'id', 'avatar')
        gridFileService.serveFile(response, file)
    }


}
