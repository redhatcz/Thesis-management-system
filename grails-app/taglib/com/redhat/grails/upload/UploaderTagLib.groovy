package com.redhat.grails.upload

import groovy.json.JsonBuilder
import org.grails.plugin.platform.util.TagLibUtils

class UploaderTagLib {

    static namespace = "u"

    def groovyPageRenderer

    def final DEF_UPLOADER_ID = 'uploader'
    def final DEF_OUTER_TEMPLATE = '/taglib/uploader/uploaderOuter'
    def final DEF_INNER_TEMPLATE = '/taglib/uploader/uploaderInner'

    def final DEF_CONFIG = [
            request: [
                    endpoint: null
            ],
            text: [:],
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
     * @attr validation Validation option map
     * @attr multiple Determines whether this uploader should accept multiple files.
     *
     */
    def uploader = { attrs, body ->
        pageScope.uploader = [:]

        def bodyResult = body()

        def uploaderId = pageScope.uploader?.id

        if (!uploaderId) {
            uploaderId = DEF_UPLOADER_ID
            bodyResult = u.body() + bodyResult
        }

        def config = buildConfig(attrs)

        // Separate wrapper attributes from remaining attributes
        def excludes = ['config', 'params', 'template', 'callbacks',
                        'topic', 'uploaderId', 'validation']
        def attrsAsString = TagLibUtils.attrsToString(attrs.findAll { !(it.key in excludes) })

        def model = [
                config: config,
                uploaderId: uploaderId,
                onComplete: attrs?.callbacks?.complete,
                callbacks: getCallbacks(attrs),
                body: bodyResult,
                attrs: attrsAsString
        ]

        def errorId = pageScope.uploader?.errorId
        if (errorId) {
            model.errorId = errorId
        }

        out << render(template: DEF_OUTER_TEMPLATE, model: model);

        pageScope.uploader = null
    }


    def trigger = {attrs, body ->

        pageScope.uploader.autoUpload = false


        def excludes = []
        def attrsAsString = TagLibUtils.attrsToString(attrs.findAll { !(it.key in excludes) })

        def model = [body: body(),
                     id: attrs.id,
                     attrs: attrsAsString]
        out << render(template: '/taglib/uploader/uploaderTrigger', model: model)
    }

     def messages = {attrs, body ->
        def template = attrs?.template
        def excludes = ['template', 'model']

        // save id of error container into pageScope
        // if id isn't specified then set default value
        pageScope.uploader.errorId = attrs.get('id', 'uploader-error')

        if (template) {
            out << render(template: template, model: attrs?.model ?: [])
        }  else {
            def attrsAsString =  TagLibUtils.attrsToString(attrs.findAll { !(it.key in excludes) })
            out << "<div$attrsAsString></div>"
        }
    }

    def body = {attrs ->

        pageScope.uploader.id = attrs.get('id', DEF_UPLOADER_ID)

        def attrsAsString = TagLibUtils.attrsToString(attrs)

        out << "<div$attrsAsString></div>"
    }

    /**
     * Creates link which can be used to delete uploaded file.
     * See g.remoteLink for more documentation.
     *
     * @param id Id used to identify the file you wish to be deleted -- Default delete listener
     *           is expecting this to be MongoId, however for user defined listeners this
     *           can be any value which will uniquely identify the file.
     *
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

        // multiple
        config.multiple = attrs?.multiple != null ? attrs.multiple : true
        // adding request params to the configuration
        config.request.params = attrs?.params ?: []
        // upload link generation workaround
        config.request.endpoint = createLink(controller: 'upload', action: 'upload',
                params: [topic: attrs.topic])
        //autoupload
        def autoUpload = pageScope?.uploader?.autoUpload
        config.autoUpload =  autoUpload != null ? autoUpload : true
        // validation
        if (attrs.validation && attrs?.validation instanceof Map){
            config.validation = attrs.validation
        }
        // text labels
        config.text = buildTextConfig(attrs)
        // uploader template
        config.template = groovyPageRenderer.render(template: attrs?.template ?: DEF_INNER_TEMPLATE);
        // classes
        config.classes = classesConfig

        def json = new JsonBuilder(config)
        json.toPrettyString()
    }

    private Map buildTextConfig(attrs) {
        def text = attrs?.text
        [
                uploadButton:       text?.uploadButton          ?:
                                    message(code: 'uploader.text.upload.button'),
                cancelButton:       text?.cancelButton          ?:
                                    message(code: 'uploader.text.cancel.button'),
                retry:              text?.retry                 ?:
                                    message(code: 'uploader.text.retry.message'),
                failUpload:         text?.failUpload            ?:
                                    message(code: 'uploader.text.failUpload.message'),
                dragZone:           text?.dragZone              ?:
                                    message(code: 'uploader.text.dragZone.title'),
                dropProcessing:     text?.dropProcessing        ?:
                                    message(code: 'uploader.text.dropProcessing.message'),
                formatProgress:     text?.formatProgress        ?:
                                    message(code: 'uploader.text.formatProgress.message'),
                waitingForResponse: text?.waitingForResponse    ?:
                                    message(code: 'uploader.text.waitingForResponse.message'),
                typeError:          text?.typeError             ?:
                                    message(code:  'uploader.text.typeError.message'),
                sizeError:          text?.sizeError             ?:
                                    message(code: 'uploader.text.sizeError.message'),
                minSizeError:       text?.minSizeError          ?:
                                    message(code: 'uploader.text.minSizeError.message'),
                emptyError:         text?.emptyError            ?:
                                    message(code: 'uploader.text.emptyError.message'),
                noFilesError:       text?.noFilesError          ?:
                                    message(code: 'uploader.text.noFilesError.message'),
                onLeave:            text?.onLeave               ?:
                                    message(code: 'uploader.text.noFilesError.message')
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
        def excludes = ['complete']
        callbacks = callbacks.findAll { !(it.key in excludes)}
        callbacks.inject('') {s, k, v  ->
            s  + ".on('$k', $v)"
        }
    }
}