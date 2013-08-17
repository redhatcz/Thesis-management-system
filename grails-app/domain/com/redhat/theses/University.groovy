package com.redhat.theses

class University {
    String name
    String acronym

    static constraints = {
        name blank: false, unique: true
        acronym blank: false, maxSize: 10
    }

    static mapping = {
        sort 'name'
    }

    String toString(){
        name
    }
}
