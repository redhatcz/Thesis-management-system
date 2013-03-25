package com.redhat.grails.mongodb

// TODO: links on files without mongoId
class GridFileTagLib {

    static namespace = "grid"

    def grailsLinkGenerator
    def gridFileService

    /**
     *
     * @param mongoId Mongo ID of requested file.
     * @param bucket Bucket name.
     * @params save If true download of the file will be force.
     *
     */
    def link = { attrs, body ->
        if (!attrs.mongoId && !attrs.object) throw new IllegalArgumentException('[mongoId] attribute must be specified for the <grid:link> tag')

        def model = attrs.findAll { !(it.key in ['mongoId', 'bucket', 'save']) }
        model.controller = 'gridFile'
        model.action = 'serveFile'
        if (!model.params) {
            model.params = [:]
        }
        model.params.mongoId = attrs.mongoId
        model.params.bucket = attrs.bucket
        model.params.save = attrs.save

        out << g.link(model, body)
    }

    /**
     *
     * @emptyTag
     *
     * @param mongoId Mongo ID of requested file.
     * @param bucket Bucket name.
     * @params save If true download of the file will be force.
     *
     */
    def createLink = { attrs ->
        if (!attrs.mongoId && !attrs.object) throw new IllegalArgumentException('[mongoId] attribute must be specified for the <grid:link> tag')

        def model = attrs.findAll { !(it.key in ['mongoId', 'bucket', 'save']) }
        model.controller = 'gridFile'
        model.action = 'serveFile'
        if (!model.params) {
            model.params = [:]
        }
        model.params.mongoId = attrs.mongoId
        model.params.bucket = attrs.bucket
        if (attrs?.save) {
            model.params.save = attrs.save
        }

        out << g.createLink(model)
    }

}
