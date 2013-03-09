package com.redhat.grails.upload.events

import groovy.transform.TupleConstructor

@TupleConstructor
class UploaderDeleteEvent {
    String id
    String bucket
    String params

    UploaderDeleteEvent(String id, String bucket, String params = [:]) {
        this.id = id
        this.bucket = bucket
        this.params = params
    }
}