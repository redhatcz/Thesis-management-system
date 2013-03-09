package com.redhat.grails.mongodb

// TODO: Support for files which are not bound to domain instances
class GridFIleTagLib {

    static namespace = "grid"

    def grailsLinkGenerator
    def gridFileService

    /**
     *
     * @param mongoId Mongo ID of requested file.
     * @param bucket Bucket name.
     */
    def link = { attrs, body ->
        if (!attrs.mongoId && !attrs.object) throw new IllegalArgumentException('[mongoId] attribute must be specified for the <grid:link> tag')
        def model = [controller: 'gridFile',
                action: 'serveFile',
                params: attrs
        ]

        out << g.link(model, body)
    }

    /**
     *
     * @emptyTag
     *
     * @param mongoId Mongo ID of requested file.
     * @param bucket Bucket name.
     */
    def createLink = { attrs ->
        if (!attrs.mongoId && !attrs.object) throw new IllegalArgumentException('[mongoId] attribute must be specified for the <grid:link> tag')
        def model = [controller: 'gridFile',
                action: 'serveFile',
                params: attrs
        ]

        out << g.createLink(model)
    }

}
