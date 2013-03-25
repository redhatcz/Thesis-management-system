package com.redhat.grails.mongodb.exceptions

class UnsupportedContentTypeException extends RuntimeException{
    UnsupportedContentTypeException() {
    }

    UnsupportedContentTypeException(String s) {
        super(s)
    }

    UnsupportedContentTypeException(String s, Throwable throwable) {
        super(s, throwable)
    }

    UnsupportedContentTypeException(Throwable throwable) {
        super(throwable)
    }
}
