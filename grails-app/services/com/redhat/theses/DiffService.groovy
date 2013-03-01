package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class DiffService {

    static transactional = false

    Map<String, List> diff(object1, object2) {
        if (object1.class != object2.class) {
            throw new IllegalArgumentException("First and second parameter of method diff must have the same type.")
        }
        def o1Props = object1.properties
        def o2Props = object2.properties

        def diff = [:]

        o1Props.each { key, value ->
            if (value != o2Props[key]) {
                diff[key] = [value, o2Props[key]]
            }
        }

        diff
    }

    String createHtmlTable(Map<String, List> diff) {
        def content = ""
        diff.each { key, value ->
            content += "<tr><td>${key}</td><td>${value[0]}</td><td>${value[1]}</td></tr>"
        }
        return '<table>' +
                    '<tr><th></th><th>Old value</th><th>New value</th></tr>' +
                    content +
               '</table>'
    }
}
