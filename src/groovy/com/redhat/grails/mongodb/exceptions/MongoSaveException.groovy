package com.redhat.grails.mongodb.exceptions

/**
 * Created with IntelliJ IDEA.
 * User: jcechace
 * Date: 2/23/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
class MongoSaveException extends RuntimeException{
    MongoSaveException() {
    }

    MongoSaveException(String s) {
        super(s)
    }

    MongoSaveException(String s, Throwable throwable) {
        super(s, throwable)
    }

    MongoSaveException(Throwable throwable) {
        super(throwable)
    }
}
