package com.redhat.theses

class University {
    String name

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
        sort 'name'
    }

    String toString(){
        name
    }
}
