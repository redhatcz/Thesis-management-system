package com.redhat.grails.upload

import groovy.json.JsonBuilder

class UploaderTagLib {

    static namespace = "u"

    def groovyPageRenderer

    def final DEF_WRAPPER_ID = 'uploader-wrapper'
    def final DEF_WRAPPER_TEMPLATE = '/taglib/uploader/uploader'
    def final DEF_TEMPLATE = '/taglib/uploader/uploaderBootstrapTemplate'

    def final DEF_CONFIG = [
            request: [
                    endpoint: null
            ],
            text: [:],
            autoUpload: false,
            multiple: true,
            template: null,
    ]

    /**
     * Ajax uploader
     *
     * @param config Config attributes in form of map structure
     * @param params Request params to be sent together with file
     * @param template Template used to generate uploader
     * @param topic Name of the topic used when UploaderEvent is fired
     * @param callbacks Map of FineUploader callbacks [callback: function]
     * @param wrapperId  Id of div used as a wrapper for uploader
     * @attr validation Validation option map
     *
     */
    def uploader = { attrs, body ->
        def config = buildConfig(attrs)

        def model = [
                config: config,
                wrapperId: attrs.wrapperId ?: DEF_WRAPPER_ID,
                callbacks: getCallbacks(attrs)
        ]

        out << render(template: DEF_WRAPPER_TEMPLATE, model: model);

    }

    /**
     * Creates link which can be used to delete uploaded file.
     * See g.remoteLink for more documentation.
     *
     * @param id Id of uploaded file (e.g. it's name, db id or any other identifier you use)
     * @param topic Name of listener used to handle this delete request.
     * @param confirm Text of the confirmation dialog. If empty no dialog will appear
     */
    def deleteLink = { attrs, body ->
        if (!attrs.id) {
            throw new IllegalArgumentException('[id] attribute must be specified for the <u:deleteLink> tag')
        }

        def model = attrs.findAll { !(it.key in ['topic', 'confirm']) }
        model.controller = 'upload'
        model.action = 'delete'

        if (attrs.confirm) {
            model.before = "if(!confirm('${attrs.confirm}')) return false"
        }
        if (!model.params) {
            model.params = [:]
        }
        model.params.topic = attrs.topic

        out << g.remoteLink(model, body)
    }

    private String buildConfig(attrs) {
        def defaultConfig = DEF_CONFIG

        def config = attrs?.config ?: defaultConfig

        // adding request params to the configuration
        config.request.params = attrs?.params ?: []
        // upload link generation workaround
        config.request.endpoint = createLink(controller: 'upload', action: 'upload',
                params: [topic: attrs.topic])
        // validation
        if (attrs.validation && attrs?.validation instanceof Map){
            config.validation = attrs.validation
        }
        // text labels
        config.text = textConfig
        // uploader template
        config.template = groovyPageRenderer.render(template: attrs?.template ?: DEF_TEMPLATE);
        // classes
        config.classes = classesConfig

        def json = new JsonBuilder(config)
        json.toPrettyString()
    }

    private Map getTextConfig() {
        [
                uploadButton: message(code: 'uploader.text.upload.button'),
                cancelButton: message(code: 'uploader.text.cancel.button'),
                retry: message(code: 'uploader.text.retry.message'),
                failUpload: message(code: 'uploader.text.failUpload.message'),
                dragZone: message(code: 'uploader.text.dragZone.title'),
                dropProcessing: message(code: 'uploader.text.dropProcessing.message'),
                formatProgress: message(code: 'uploader.text.formatProgress.message'),
                waitingForResponse: message(code: 'uploader.text.waitingForResponse.message'),
                typeError: message(code:  'uploader.text.typeError.message'),
                sizeError: message(code: 'uploader.text.sizeError.message'),
                minSizeError: message(code: 'uploader.text.minSizeError.message'),
                emptyError: message(code: 'uploader.text.emptyError.message'),
                noFilesError: message(code: 'uploader.text.noFilesError.message'),
                onLeave: message(code: 'uploader.text.noFilesError.message')
        ]
    }

    private Map getClassesConfig() {
        [
                success: 'alert alert-success',
                fail: 'alert alert-error'
        ]
    }

    private String getCallbacks(attrs) {
        Map callbacks = attrs.callbacks ?: [:]
        callbacks.inject('') {s, k, v  ->
            s  + ".on('$k', $v)"
        }
    }
}