package com.redhat.theses

class Faq {
    String question
    String answer
    Locale locale = Locale.ENGLISH

    static constraints = {
        question unique: true, blank: false
        answer blank: false
    }

    static mapping = {
        answer type: 'text'
    }
}
