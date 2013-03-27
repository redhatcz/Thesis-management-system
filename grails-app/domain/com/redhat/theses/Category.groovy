package com.redhat.theses

class Category {

    String title
    String description

    static constraints = {
        title unique: true, blank: false
        description maxSize: 255, nullable: true
    }

    static mapping = {
        sort 'title'
    }

    String toString(){
        title
    }
}