package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class Organization {
    String name

    static constraints = {
        name blank: false, unique: true
    }

    String toString(){
        name
    }
}
