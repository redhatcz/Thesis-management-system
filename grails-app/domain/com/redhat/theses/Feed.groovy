package com.redhat.theses

import grails.converters.JSON

class Feed {
    String messageCode
    String argsJSON
    Date dateCreated

    static constraints = {
        messageCode blank: false
        argsJSON nullable: true
    }

    static mapping = {
        messageCode length: 255
        argsJSON length: 65535
        version false
    }

    List<String> getArgs() {
        (List<String>) JSON.parse(argsJSON)
    }

    def setArgs(List<String> args) {
        argsJSON = args as JSON
    }
}
