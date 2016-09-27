package com.redhat.theses

class DateRangePickerTagLib {

    def dateRangePicker = {attrs ->
        def fromId = attrs.id + "From"
        def toId = attrs.id + "To"

        def fromPlaceholder = ""
        def toPlaceholder = ""

        if (attrs.placeholderMsg) {
            fromPlaceholder = g.message(code: attrs.placeholderMsg + ".from")
            toPlaceholder = g.message(code: attrs.placeholderMsg + ".to")
        }

        def toValue = attrs.params?."${attrs?.name}/to" ?: ""
        def fromValue = attrs.params?."${attrs?.name}/from" ?: ""

        def divId = attrs.id + "Div"

        out << g.render(template: '/taglib/datePicker/dateRangePicker',
                        model: [name: attrs.name, label: attrs.label, from: [id: fromId, placeholder: fromPlaceholder,
                                value: fromValue], to: [id: toId, placeholder: toPlaceholder, value: toValue], divId: divId])
    }
}
