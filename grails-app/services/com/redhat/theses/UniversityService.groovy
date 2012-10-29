package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class UniversityService {

    Boolean saveWithMemberships(University university, List<Membership> memberships) {
        def allMemberships = Membership.findAllByUniversity(university)
        def allMembers = allMemberships.collect { it.user }
        def currentMembers = memberships.collect { it.user }
        def toBeSaved = memberships.grep { !(it.user in allMembers) }
        def toBeDeleted = allMemberships.grep { it.user in currentMembers }

        def success = university.save() && toBeSaved.every { it.save() } && toBeDeleted.every { delete(it) }
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    Boolean deleteWithMemberships(University university, List<Membership> memberships) {
        def success = memberships.each { delete(it) } && delete(university)
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
