package com.redhat.theses.util

import org.springframework.dao.DataIntegrityViolationException

/**
 * @author vdedik@redhat.com
 */
class Commons {

    /**
     * Deletes entity safely (without throwing DataIntegrityViolationException exception but returning false instead)
     *
     * @param entity - GORM domain object
     * @return true if successfully deleted, false if not
     */
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
