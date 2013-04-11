package com.redhat.theses

class Ajax4GSPTagLib {
    static namespace = "a4g"

    /**
     * Autocomplete textField.
     *
     * @attr data-autocomplete-url - url of the ajax request
     * @attr data-autocomplete-target - id of element that will be populated with the value of the selected item
     * @attr data-autocomplete-opts - elements that will be a part of the query string, separated by one whitespace, you can use
     *    '@' symbol if you want to change the name of the query string parameter, otherwise, the name of the selected
     *    element will be used as the query string parameter
     */
    def textField = { attrs, body ->
        def id = attrs.id
        if (!id) {
            id = attrs.name
        }
        out << g.textField(attrs)
        out << "<script type='text/javascript'>autocomplete('${id}')</script>"
    }

    /**
     * Autocomplete select
     */
    def select = { attrs, body ->
        def id = attrs.id
        if (!id) {
            id = attrs.name
        }

        if (!attrs.from) {
            attrs.from = []
        }

        out << g.select(attrs)
        out << "<script type='text/javascript'>dynamicSelect('${id}')</script>"
    }
}
