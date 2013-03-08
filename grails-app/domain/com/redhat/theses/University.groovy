package com.redhat.theses

class University {
    String name

    static constraints = {
        name blank: false, unique: true
    }

    String toString(){
        name
    }
}
