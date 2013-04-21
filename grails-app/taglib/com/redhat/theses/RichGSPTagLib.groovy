package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Util
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.web.pages.discovery.GrailsConventionGroovyPageLocator
import org.grails.plugin.platform.util.TagLibUtils
import org.springframework.beans.SimpleTypeConverter
import org.springframework.context.MessageSourceResolvable

class RichGSPTagLib {
    /**
     *  Dependency injection of com.redhat.grails.mongodb.GridFileService
     */
    def gridFileService

    /**
     * Dependency injection of org.codehaus.groovy.grails.web.pages.discovery.GrailsConventionGroovyPageLocator
     */
    GrailsConventionGroovyPageLocator groovyPageLocator

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

    /**
     * Creates multiCheckBox - a list of checkboxes, similar to multiple select
     *
     * @attr from - list of items
     * @attr value - list of items that will be checked by default
     * @attr optionKey - name of the property that will be used as the key
     * @attr optionLabel - name of the property that will be used as the label
     * @attr useIndex - boolean whether or not to use index in names of generated checkboxes
     */
    def multiCheckBox = { attrs, body ->
        def from = attrs.from
        def name = attrs.name
        def id = attrs.id ?: name
        def classes = attrs['class']
        def value = attrs.value
        def optionKey = attrs?.optionKey
        def optionLabel = attrs?.optionLabel
        def useIndex =  attrs?.useIndex != null ? attrs.useIndex : true
        def result = ''
        def isChecked

        from?.eachWithIndex { item, i ->
            def localValue = optionKey ? item."${optionKey}" : item.toString()
            def index = useIndex ? "[$i]" : ''
            isChecked = value?.find {
                (optionKey ? it."${optionKey}" : it.toString()) == localValue
            }

            def model = [name: optionLabel ? "${name}$index.${optionKey}" : "${name}$index",
                         id: "${id}[$i]",
                         value: localValue,
                         label: optionLabel ? item."${optionLabel}" : item?.toString(),
                         checked: isChecked,
                         classes: classes]
            result += render(template: '/taglib/richg/multiCheckBoxInner', model: model)
        }

        def modelOuter = [id: attrs.id, body: result]
        out << render(template: '/taglib/richg/multiCheckBoxOuter', model: modelOuter);
    }

    /**
     * A helper tag for creating multiselect element (checkbox based select).<br/>
     *
     * @emptyTag
     *
     * @attr name REQUIRED the select name
     * @attr index If evaluates to true the name will be used in form of list
     * @attr id the DOM element id - uses the name attribute if not specified
     * @attr from REQUIRED The list or range to select from
     * @attr keys A list of values to be used for the value attribute of each "option" element.
     * @attr optionKey By default value attribute of each &lt;option&gt; element will be the result of a "toString()" call on each element. Setting this allows the value to be a bean property of each element in the list.
     * @attr optionValue By default the body of each &lt;option&gt; element will be the result of a "toString()" call on each element in the "from" attribute list. Setting this allows the value to be a bean property of each element in the list.
     * @attr value The current selected value that evaluates equals() to true for one of the elements in the from list.
     * @attr valueMessagePrefix By default the value "option" element will be the result of a "toString()" call on each element in the "from" attribute list. Setting this allows the value to be resolved from the I18n messages. The valueMessagePrefix will be suffixed with a dot ('.') and then the value attribute of the option to resolve the message. If the message could not be resolved, the value is presented.
     */
    def multiselect = { attrs ->
        //TODO: Support for html attributes
        if (!attrs.name) {
            throwTagError("Tag [multiselect] is missing required attribute [name]")
        }
        if (!attrs.containsKey('from')) {
            throwTagError("Tag [multiselect] is missing required attribute [from]")
        }

        def name = attrs.remove('name')
        def index = attrs.remove('index')
        def from = attrs.remove('from')
        def keys = attrs.remove('keys')
        def optionKey = attrs.remove('optionKey')
        def optionValue = attrs.remove('optionValue')
        def value = attrs.remove('value')
        def valueMessagePrefix = attrs.remove('valueMessagePrefix')
        def body = new StringBuilder()

        if (from) {
            from.eachWithIndex { el, i ->
                def keyValue = null
                def keyValueObject = null
                def label = null
                def cname = name

                // Value
                if (keys) {
                    keyValue = keys[i]
                } else if (optionKey) {
                    if (optionKey instanceof Closure) {
                        keyValue = optionKey(el)
                    } else if (el != null && optionKey == 'id' && grailsApplication.getArtefact(DomainClassArtefactHandler.TYPE, el.getClass().name)) {
                        keyValue = el.ident()
                        keyValueObject = el
                    } else {
                        keyValue = el[optionKey]
                        keyValueObject = el
                    }
                } else {
                    keyValue = el
                }

                // Label
                if (optionValue) {
                    if (optionValue instanceof Closure) {
                        label = optionValue(el).toString().encodeAsHTML()
                    } else {
                        label = el[optionValue].toString().encodeAsHTML()
                    }
                } else if (el instanceof MessageSourceResolvable) {
                    label = messageSource.getMessage(el, locale)
                } else if (valueMessagePrefix) {
                    def message = messageSource.getMessage("${valueMessagePrefix}.${keyValue}", null, null, locale)
                    if (message != null) {
                        label = message.encodeAsHTML()
                    } else if (keyValue && keys) {
                        def s = el.toString()
                        if (s) writer << s.encodeAsHTML()
                    } else if (keyValue) {
                        label = keyValue.encodeAsHTML()
                    } else {
                        def s = el.toString()
                        if (s) label = s.encodeAsHTML()
                    }
                } else {
                    def s = el.toString()
                    if (s)label = s.encodeAsHTML()
                }

                if (index) {
                    cname = "${name}[$i].${optionKey}"
                }
                def checkbox = renderMultiselectCheckbox(cname, label, keyValue, value, keyValueObject)

                body.append(checkbox)
            }
        }


        def model = [body: body]
        out << render(template: '/taglib/richg/multiselectOuter', model: model)
    }


    private renderMultiselectCheckbox(name, label, keyValue, value, el) {

        boolean checked = false
        def keyName = name
        def keyClass = keyValue?.getClass()
        if (keyClass.isInstance(value)) {
            checked = (keyValue == value)
        }
        else if (value instanceof Collection) {
            // first try keyValue
            checked = value.contains(keyValue)
            if (! checked && el != null) {
                checked = value.contains(el)
            }
        }
        // GRAILS-3596: Make use of Groovy truth to handle GString <-> String
        // and other equivalent types (such as numbers, Integer <-> Long etc.).
        else if (keyValue == value) {
            checked = true
        }
        else if (keyClass && value != null) {
            try {
                def typeConverter = new SimpleTypeConverter()
                value = typeConverter.convertIfNecessary(value, keyClass)
                checked = (keyValue == value)
            }
            catch (e) {
                // ignore
            }
        }

//        if (i != null) {
//            keyName = "$name[$i]"
//        }

        def model = [name: keyName,
                     value: keyValue,
                     label: label,
                     checked: checked ? 'checked' : '']
        render(template: '/taglib/richg/multiselectInner', model: model)
    }
/**
     * The same as g:link but adds property removeParams
     *
     * @attr removeParams - params to be removed from the url
     */
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

    /**
     * Test if resource exists, if so, renders the body, you can use either template name or view name
     *
     * @attr template - template name
     * @attr view - view name
     */
    def ifResourceExists = { attrs, body ->
        def resFile

        if (attrs?.template) {
            if (attrs?.template?.startsWith('/')) {
                resFile = groovyPageLocator.findTemplateByPath(attrs?.template)
            } else {
                resFile = groovyPageLocator.findTemplate(params.controller, attrs?.template)
            }
        } else if (attrs?.view) {
            if (attrs?.view?.startsWith('/')){
                resFile = groovyPageLocator.findViewByPath(attrs?.view)
            } else {
                resFile = groovyPageLocator.findView(params.controller, attrs?.view)
            }
        } else {
            return
        }

        if (resFile) {
            out << body()
        }
    }

    /**
     * Generets url of given users avatar
     *
     * @EmptyTag
     *
     * @attr user User
     * @attr small If true small version of avatar will be used
     *
     */
    def avatarLink = { attrs, body ->
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

        out << uri
    }

}
