package com.redhat.theses

class JQueryTagLib {

    static namespace = "jQuery"

    def escapeSelector = { attrs, body ->
        out << attrs.value.replaceAll(/([\[\]\.])/, '\\\\\\\\$1');
    }

}
