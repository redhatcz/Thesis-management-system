package com.redhat.theses

import grails.converters.JSON

class AutocompleteTagLib {
    static namespace = "autocomplete"

    def textField = { attrs, body ->
        def model = [name: attrs.name, value: attrs.value,
                     hiddenFieldId: attrs.hiddenFieldId,
                     remoteUrl: attrs.remoteUrl,
                     optElements: attrs.optElements]
        out << render(template: "/taglib/autocomplete/textField", model: model)
    }
}
