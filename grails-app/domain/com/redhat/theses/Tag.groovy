package com.redhat.theses

class Tag implements Serializable {
    String title

    static mapping = {
        id composite: ['title']
        version false
        sort 'title'
    }

    String toString(){
        title
    }
}
