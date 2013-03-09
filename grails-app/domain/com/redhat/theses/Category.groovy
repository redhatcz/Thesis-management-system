package com.redhat.theses

class Category {

    String title
    String description

    static constraints = {
        title   unique: true
        description maxSize: 255, nullable: true
    }

    String toString(){
        title
    }
}