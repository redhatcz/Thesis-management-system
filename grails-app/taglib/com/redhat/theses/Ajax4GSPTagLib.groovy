package com.redhat.theses

class Ajax4GSPTagLib {
    static namespace = "a4g"

    def autocomplete = { attrs, body ->
        def model = [name: attrs.name, value: attrs.value,
                     hiddenFieldId: attrs.hiddenFieldId,
                     remoteUrl: attrs.remoteUrl,
                     optElements: attrs.optElements]
        out << render(template: "/taglib/autocomplete/textField", model: model)
    }
}
