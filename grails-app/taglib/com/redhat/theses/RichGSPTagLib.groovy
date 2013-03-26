package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util
import org.grails.plugin.platform.util.TagLibUtils

class RichGSPTagLib {
    /**
     *  Dependency injection of com.redhat.grails.mongodb.GridFileService
     */
    def gridFileService

    static namespace = "richg"

    /**
     * This will create dynamic number of the content, usually dynamic number of textFields that the user can either
     * increase or decrease.
     *
     * @attr id - id of the enclosing div
     * @attr for - collection that will be used to initialize the dynamic list
     * @attr var - the name of the variable that will be used to store every item from the collection 'for'
     * @attr index - status of the collection
     * @attr class - additional classes of enclosing div (by default dynamic-filed  is set)
     * @attr childClass - additional classes of each child element (by default dynamic-filed-child is set
     * @attrs initSize - number of empty child elements displayed
     */
    def dynamicField = { attrs, body ->
        def result = ""

        def var = attrs?.var;
        def index = attrs?.index;
        def list = attrs?.for;
        def size = list?.size() ?: 0
        def initSize = attrs?.initSize != null ? attrs.initSize.toInteger() : 0

        def cloneModel = [var: var, i: "clone",
                body: body((index): "clone"),
                classes: "dynamic-field-child clone ${attrs?.childClass}",
                id: "dynamic-field-${var}-clone"]
        result += render(template: "/taglib/richg/dynamicFieldInner", model: cloneModel)

        list?.eachWithIndex { item, i ->
            def model = [var: var, i: i,
                    body: body((var): item, (index): i),
                    classes: "dynamic-field-child ${attrs?.childClass}",
                    id: "dynamic-field-${var}-${i}"]
            result += render(template: "/taglib/richg/dynamicFieldInner", model: model)
        }

        initSize.times {
            def i = size + it
            def model = [var: var, i: i,
                    body: body((var): null, (index): i),
                    classes: "dynamic-field-child ${attrs?.childClass}",
                    id: "dynamic-field-${var}-${i}"]
            result += render(template: "/taglib/richg/dynamicFieldInner", model: model)
        }


        def modelOuter = [id: attrs?.id, var: var,
                classes: attrs?.class,
                size: initSize + size,
                body: result]
        out << render(template: "/taglib/richg/dynamicFieldOuter", model: modelOuter)
    }

    /**
     * Creates alert @message (or @code) of @type (success, info, error or warning)
     *
     * @attr message - message to be printed
     * @attr code - code of the message
     * @attr args - arguments of the message
     * @attr type - type of the message
     */
    def alert = { attrs, body ->
        def message
        if (attrs.code) {
            message = g.message(code: attrs.code, args: attrs.args)
        } else {
            message = attrs.message
        }

        out << render(template: '/taglib/richg/alert',
                model: [type: attrs.type, message: message])
    }

    /**
     * Creates comment with id comment-@index from @comment
     * // TODO: customizability
     */
    def comment = { attrs, body ->
        out << render(template: '/taglib/richg/comment',
                model: [comment: attrs.comment, index: attrs.index])
    }

    /**
     * Creates comments component from @comments for @article
     * // TODO: some more customizability, like url of ajax etc
     */
    def comments = { attrs, body ->
        out << render(template: '/taglib/richg/comments',
                model: [comments: attrs.comments, article: attrs.article, commentsTotal: attrs.commentsTotal])
    }

    def multiCheckBox = { attrs, body ->
        def from = attrs.from
        def name = attrs.name
        def id = attrs.id ?: name
        def classes = attrs['class']
        def value = attrs.value
        def optionKey = attrs.optionKey
        def result = ''
        def isChecked

        from?.eachWithIndex { item, i ->
            isChecked = value?.find { it?."${optionKey}" == item."${optionKey}" }

            def model = [name: "${name}[${i}].${optionKey}",
                    id: "${id}[${i}]",
                    value: item."${optionKey}",
                    label: item."${attrs.label}",
                    checked: isChecked,
                    classes: classes
            ]
            result += render(template: '/taglib/richg/multiCheckBoxInner', model: model)
        }

        def modelOuter = [id: attrs.id, body: result]
        out << render(template: '/taglib/richg/multiCheckBoxOuter', model: modelOuter);
    }

    def link = { attrs, body ->
        def attrsParams = attrs?.params
        // remove all 'remove params' and 'params' because multiple params are not currently supported
        def removeParams = attrs.removeParams ?: []
        removeParams = removeParams + attrsParams.keySet()

        attrs?.params = Util.formatParams(request, attrsParams, removeParams)

        out << g.link(attrs, { body() })
    }

    /**
     * Generets <img> tag with the source url of given users avatar
     *
     * @attr user User
     * @attr small If true small version of avatar will be used
     *
     */
    def avatar = { attrs, body ->
        def user = attrs?.user
        def group = attrs?.small ? 'avatar_small' : 'avatar'

        if (!(user instanceof User)) {
            throw IllegalArgumentException('user')
        }

        def avatar = gridFileService.getBoundFile(user, 'id', group)
        def uri

        if (avatar) {
            uri = grid.createLink(mongoId: avatar?.id?.toString(), bucket: User.bucketMapping)
        } else {
            uri = resource(dir: 'images', file: attrs?.small ? 'avatar-mini.png' : 'avatar.png')
        }

        def excludes = ['user', 'small']
        def attrsAsString = TagLibUtils.attrsToString(attrs.findAll { !(it.key in excludes) })

        out << "<img src=\"${uri.encodeAsHTML()}\"${attrsAsString} />"
    }
}
