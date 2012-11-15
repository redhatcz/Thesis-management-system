package com.redhat.theses

class RichGSPTagLib {
    static namespace = "richg"

    /**
     * This will create dynamic number of the content, usually dynamic number of textFields that the user can either
     * increase or decrease.
     *
     * Attributes
     *  - id - id of the enclosing div
     *  - for - collection that will be used to initialize the dynamic list
     *  - var - the name of the variable that will be used to store every item from the collection 'for'
     *  - index - status of the collection
     */
    def dynamicField = { attrs, body ->
        def result = ""

        def var = attrs?.var;
        def index = attrs?.index;
        def list = attrs?.for;

        def cloneModel = [var: var, i: "clone",
                          body: body((index): "clone"),
                          classes: "dynamic-field-child clone",
                          id: "dynamic-field-${var}-clone"]
        result += render(template: "/taglib/richg/dynamicFieldInner", model: cloneModel)

        list?.eachWithIndex { item, i ->
            def model = [var: var, i: i,
                         body: body((var): item, (index): i),
                         classes: "dynamic-field-child",
                         id: "dynamic-field-${var}-${i}"]
            result += render(template: "/taglib/richg/dynamicFieldInner", model: model)
        }

        def modelOuter = [id: attrs?.id, var: var,
                          size: list?.size() ?: 0 , body: result]
        out << render(template: "/taglib/richg/dynamicFieldOuter", model: modelOuter)
    }

    /**
     * Creates alert @message of @type (success, info, error or warning)
     */
    def alert = { attrs, body ->
        out << render(template: '/taglib/richg/alert',
                model: [type: attrs.type, message: attrs.message])
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
}
