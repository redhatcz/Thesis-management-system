package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class OrganizationService {

    Boolean saveWithMemberships(Organization organization, List<Membership> memberships) {
        def allMemberships = organization.id ? Membership.findAllByOrganization(organization) : []
        def allMembers = allMemberships.collect { it.user }
        def currentMembers = memberships.collect { it.user }
        def toBeSaved = memberships.findAll { !(it.user in allMembers) }
        def toBeDeleted = allMemberships.findAll { !(it.user in currentMembers) }

        def success = organization.save() && toBeSaved.every { it.save() } && toBeDeleted.every { delete(it) }
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    Boolean deleteWithMemberships(Organization organization, List<Membership> memberships) {
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
