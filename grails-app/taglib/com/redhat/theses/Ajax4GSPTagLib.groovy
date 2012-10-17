package com.redhat.theses

class Ajax4GSPTagLib {
    static namespace = "a4g"

    /**
     * Adds autocomplete "magic" on textfield inside this element.
     * If you wish, you can provide a hidden element that will be populated with ID returned from the server.
     */
    def autocomplete = { attrs, body ->
        def content = body()
        def xmlContent = new XmlSlurper().parseText("<root>" + content + "</root>")

        // get ID of the textfield
        def inputFieldId = xmlContent.input.findAll { it.@type == 'text' }.@id
        def model = [inputFieldId: inputFieldId,
                     remoteUrl: attrs.remoteUrl,
                     optElements: attrs.optElements]

        // get ID of the hiddenfield and if it exists, put it in the model
        def outputFieldId = xmlContent.input.findAll { it.@type == 'hidden' }.@id
        if (outputFieldId) {
            model += [outputFieldId: outputFieldId]
        }

        // render output
        out << content
        out << render(template: "/taglib/a4g/autocomplete", model: model)
    }
}
