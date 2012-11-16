package com.redhat.theses.util

/**
 * @author vdedik@redhat.com
 */
class Util {

    public static final Integer DEFAULT_MAX = 10
    public static final Integer MAX_LIMIT = 100

    public static Integer max(maximum) {
        if (maximum instanceof String) {
            maximum = parseInt(maximum)
        }
        Math.min(maximum ?: DEFAULT_MAX, MAX_LIMIT)
    }

    public static Integer lastOffset(total, maximum = null) {
        total - (total % Util.max(maximum))
    }

    public static Integer lastOffset(total, maximum, currentOffset) {
        if (currentOffset == null) {
            lastOffset(total, maximum)
        } else {
            parseInt(currentOffset)
        }
    }

    public static Integer parseInt(string) {
        try {
            Integer.parseInt(string)
        } catch (NumberFormatException e) {
            null
        }
    }
}
