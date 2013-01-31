package com.redhat.theses.util

import javax.servlet.http.HttpServletRequest

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

    static Boolean hasAnyDomain(String email, List<String> domains) {
        domains.any {hasDomain(email, it)}
    }

    private static List getControllerAndAction(HttpServletRequest request) {
        String uri = request.forwardURI - request.contextPath
        uri.substring(1).split('/')
    }
}
