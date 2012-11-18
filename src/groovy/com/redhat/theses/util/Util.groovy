package com.redhat.theses.util

import javax.servlet.http.HttpServletRequest

/**
 * @author vdedik@redhat.com
 */
class Util {

    public static final Integer DEFAULT_MAX = 10
    public static final Integer MAX_LIMIT = 100

    public static Integer max(maximum) {
        maximum = toInt(maximum)
        Math.min(maximum ?: DEFAULT_MAX, MAX_LIMIT)
    }

    public static Integer lastOffset(total, maximum = null) {
        total - ((total % Util.max(maximum)) ?: DEFAULT_MAX)
    }

    public static Integer lastOffset(total, maximum, currentOffset) {
        if (currentOffset == null) {
            lastOffset(total, maximum)
        } else {
            toInt(currentOffset)
        }
    }

    public static Integer toInt(object) {
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

    public static Boolean isPaginationVisible(total, max) {
        toInt(total) > Util.max(max)
    }

    public static Boolean isActionInUrl(HttpServletRequest request, String action) {
        getControllerAndAction(request)[1] == action
    }

    public static Boolean isControllerInUrl(HttpServletRequest request, String controller) {
        getControllerAndAction(request)[0] == controller
    }

    public static Boolean isControllerOrActionInUrl(HttpServletRequest request, String controller, String action) {
        isControllerInUrl(request, controller) || isActionInUrl(request, action)
    }

    private static List getControllerAndAction(HttpServletRequest request) {
        String uri = request.forwardURI - request.contextPath
        uri.substring(1).split('/')
    }
}
