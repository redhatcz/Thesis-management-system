package com.redhat.theses

class Faq {
    String question
    String answer
    Locale locale = Locale.ENGLISH

    static constraints = {
        question unique: true, nullable: false, blank: false
        answer nullable: false, blank: false
    }

    static mapping = {
        answer type: 'text'
    }
}
