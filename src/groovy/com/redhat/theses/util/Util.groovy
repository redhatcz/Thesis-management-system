package com.redhat.theses.util

/**
 * @author vdedik@redhat.com
 */
class Util {

    public static final Integer DEFAULT_MAX = 10
    public static final Integer MAX_LIMIT = 100

    public static Integer max(maximum) {
        if (maximum instanceof String) {
            maximum = toInt(maximum)
        }
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
        toInt(total) > toInt(max)
    }
}
