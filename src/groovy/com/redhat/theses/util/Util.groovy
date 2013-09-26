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

    /**
     * Returns either the value of maximum as integer or the defaultMaximum
     *
     * @param maximum - maximum
     * @param defaultMaximum - default maximum
     * @return maximum if maximum is not null, defaultMaximum otherwise
     */
    static Integer max(maximum, defaultMaximum = DEFAULT_MAX) {
        maximum = toInt(maximum)
        Math.min(maximum ?: defaultMaximum, MAX_LIMIT)
    }

    /**
     * Returns last offset of pagination
     *
     * @param total - number of all items to be paginated
     * @param maximum (optional) - maximum of items on one page
     * @return last offset
     */
    static Integer lastOffset(total, maximum = null) {
        total - ((total % Util.max(maximum)) ?: DEFAULT_MAX)
    }

    /**
     * The same as lastOffset(total, maximum), but if currentOffset is not null, returns currentOffset
     *
     * @return last offset if currentOffset is null, currentOffset otherwise
     */
    static Integer lastOffset(total, maximum, currentOffset) {
        if (currentOffset == null) {
            lastOffset(total, maximum)
        } else {
            toInt(currentOffset)
        }
    }

    /**
     * Converts any object of any type to integer and returns null if object is not parseable
     *
     * @param object - any object
     * @return number representation of the object or null if not possible to convert
     */
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

    /**
     * Test if pagination should be visible
     *
     * @param total - number of all items to be paginated
     * @param max - maximum of items on one page
     * @return true if there is more that one page, false otherwise
     */
    static Boolean isPaginationVisible(total, max) {
        toInt(total) > Util.max(max)
    }

    /**
     * Test if action is included in URL
     *
     * @param request - http request
     * @param action - action name
     * @return true if action is included in URL, false otherwise
     */
    static Boolean isActionInUrl(HttpServletRequest request, String action) {
        getControllerAndAction(request)[1] == action
    }

    /**
     * Test if controller is included in URL
     *
     * @param request - http request
     * @param controller - controller name
     * @return true if controller is included in URL, false otherwise
     */
    static Boolean isControllerInUrl(HttpServletRequest request, String controller) {
        getControllerAndAction(request)[0] == controller
    }

    /**
     * Test if either controller or url is included in URL
     *
     * @param request - http request
     * @param action - action name
     * @param controller - controller name
     * @return true if controller or action is included in URL, false otherwise
     */
    static Boolean isControllerOrActionInUrl(HttpServletRequest request, String controller, String action) {
        isControllerInUrl(request, controller) || isActionInUrl(request, action)
    }

    /**
     * Test if the given email is hosted at the given domain
     *
     * @param email - email address
     * @param domain - domain address
     * @return true if email address is hosted at the given domain, false otherwise
     */
    static Boolean hasDomain(String email, String domain) {
        email =~ /@${domain.replaceAll('.', '\\.')}$/
    }

    /**
     * Test if the given email is hosted at any of the given domain addresses
     *
     * @param email - email address
     * @param domains - list of domain addresses
     * @return true if email address is hosted at any of the given domain addresses, false otherwise
     */
    static Boolean hasAnyDomain(String email, domains) {
        if (domains instanceof String) {
            domains = [domains]
        }
        domains.any {hasDomain(email, it)}
    }

    /**
     * Replaces all whitespaces with dashes, removes foreign characters and transforms the string to lowercase
     *
     * @param string - string to be hyphenized
     * @return - hyphenized string
     */
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

    /**
     * Converts all request params to Map removing removeParams and adding params
     *
     * @param request - http request
     * @param params - params to be included
     * @param removeParams - params to be removed
     * @return Map of params
     */
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
