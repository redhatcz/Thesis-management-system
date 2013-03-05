package com.redhat.grails.upload.events

import groovy.transform.TupleConstructor
import org.springframework.web.multipart.MultipartFile

@TupleConstructor
class UploaderEvent {
    MultipartFile file
    Map params

    UploaderEvent(MultipartFile file, Map params = []) {
        this.file = file
        this.params = params
    }
}
