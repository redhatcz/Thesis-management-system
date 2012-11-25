package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class OrganizationService {

    Boolean deleteWithMemberships(Organization organization) {
        def memberships = Membership.findAllByOrganization(organization)
        def success = memberships.each { delete(it) } && delete(organization)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    private Boolean delete(entity) {
        def success
        try {
            entity.delete()
            success = true
        } catch (DataIntegrityViolationException e) {
            success = false
        }
        success
    }
}
