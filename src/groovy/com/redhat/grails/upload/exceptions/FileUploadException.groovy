package com.redhat.grails.upload.exceptions

/**
 * Created with IntelliJ IDEA.
 * User: jcechace
 * Date: 2/10/13
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
class FileUploadException extends RuntimeException {
    FileUploadException() {
    }

    FileUploadException(String s) {
        super(s)
    }

    FileUploadException(String s, Throwable throwable) {
        super(s, throwable)
    }

    FileUploadException(Throwable throwable) {
        super(throwable)
    }
}
