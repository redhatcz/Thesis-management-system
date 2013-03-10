package com.redhat.grails.upload.events

import groovy.transform.TupleConstructor

@TupleConstructor
class UploaderDeleteEvent {
    String id
    Map params

    UploaderDeleteEvent(String id, Map params = [:]) {
        this.id = id
        this.params = params
    }
}