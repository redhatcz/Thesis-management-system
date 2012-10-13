package com.redhat.theses

class AutocompleteTagLib {
    static namespace = "autocomplete"

    def textField = { attrs, body ->
        def model = [name: attrs.name, value: attrs.value,
                     hiddenFieldId: attrs.hiddenFieldId,
                     remoteUrl: attrs.remoteUrl]
        out << render(template: "/taglib/autocomplete/textField", model: model)
    }
}
