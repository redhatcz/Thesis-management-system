package com.redhat.theses
import com.redhat.theses.auth.User

class Feed {
    User user
    String messageCode
    List args
    Date dateCreated

    static mapWith = "mongo"

    static constraints = {
        messageCode blank: false
    }

    static mapping = {
        messageCode length: 255
        version false
    }
}
