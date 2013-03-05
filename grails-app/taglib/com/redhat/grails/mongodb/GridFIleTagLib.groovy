package com.redhat.grails.mongodb

class GridFIleTagLib {

    static namespace = "grid"

    def grailsLinkGenerator
    def gridFileService

    def link = { attrs, body->
        if (!attrs.mongoId && !attrs.object) throw new IllegalArgumentException('[mongoId] attribute must be specified for the <grid:link> tag')
        def model = [controller: 'gridFile',
                     action: 'serveFile',
                     params: attrs
                     ]

        out << g.link(model, body)
    }

}
