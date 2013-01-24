package com.redhat.theses.util

import org.springframework.dao.DataIntegrityViolationException

/**
 * @author vdedik@redhat.com
 */
class Commons {
    static Boolean delete(entity) {
        def success
        try {
            entity.delete(flush: true)
            success = true
        } catch (DataIntegrityViolationException e) {
            success = false
        }
        success
    }
}
