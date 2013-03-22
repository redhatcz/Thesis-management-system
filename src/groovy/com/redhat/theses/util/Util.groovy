package com.redhat.theses.util

import org.codehaus.groovy.grails.web.util.WebUtils
import javax.servlet.http.HttpServletRequest
import java.text.Normalizer
/**
 * @author vdedik@redhat.com
 */
class Util {

    public static final Integer DEFAULT_MAX = 10
    public static final Integer MAX_LIMIT = 100

    static Integer max(maximum) {
        maximum = toInt(maximum)
        Math.min(maximum ?: DEFAULT_MAX, MAX_LIMIT)
    }

    static Integer lastOffset(total, maximum = null) {
        total - ((total % Util.max(maximum)) ?: DEFAULT_MAX)
    }

    static Integer lastOffset(total, maximum, currentOffset) {
        if (currentOffset == null) {
            lastOffset(total, maximum)
        } else {
            toInt(currentOffset)
        }
    }

    static Integer toInt(object) {
        def result = 0
        if (object instanceof String) {
            try {
                result = Integer.parseInt(object)
            } catch (NumberFormatException e) {
            }
        } else if (object instanceof Number) {
            result = object
        }
        result
    }

    static Integer nextOffset(currentOffset, maximum) {
        (toInt(currentOffset) ?: 0) + max(maximum)
    }

    static Integer previousOffset(currentOffset, maximum) {
        toInt(currentOffset) - max(maximum)
    }

    static Boolean isLastPage(total, maximum, offset) {
        (toInt(total) - toInt(offset)) <= max(maximum)
    }

    static Boolean isFirstPage(offset) {
        offset == null || toInt(offset) == 0
    }

    static Boolean isPaginationVisible(total, max) {
        toInt(total) > Util.max(max)
    }

    static Boolean isActionInUrl(HttpServletRequest request, String action) {
        getControllerAndAction(request)[1] == action
    }

    static Boolean isControllerInUrl(HttpServletRequest request, String controller) {
        getControllerAndAction(request)[0] == controller
    }

    static Boolean isControllerOrActionInUrl(HttpServletRequest request, String controller, String action) {
        isControllerInUrl(request, controller) || isActionInUrl(request, action)
    }

    static Boolean hasDomain(String email, String domain) {
        email =~ /@${domain.replaceAll('.', '\\.')}$/
    }

    static Boolean hasAnyDomain(String email, domains) {
        domains.any {hasDomain(email, it)}
    }

    static String hyphenize(String string) {
        // Decompose any funny characters.
        def link = Normalizer.normalize(string, Normalizer.Form.NFKD)

        // Spaces to dashes.
        link = link.replace(' ', '-')

        // Keep only word characters ([a-zA-Z_0-9]) and the dash.
        link = link.replaceAll(/[^-\w]/, '')

        link = link.toLowerCase()

        return link
    }

    static Map formatParams(request, Map params = [:], List removeParams = []) {
        // remove all 'remove params' and 'params' because multiple params are not currently supported
        def allRemoveParams = removeParams + params.keySet()

        def result = WebUtils.fromQueryString(request.queryString ?: '')
        allRemoveParams.each {
            result.remove(it)
        }

        params.each {key, val ->
            result[key] = val
        }
        return result
    }

    private static List getControllerAndAction(HttpServletRequest request) {
        String uri = request.forwardURI - request.contextPath
        uri.substring(1).split('/')
    }
}
