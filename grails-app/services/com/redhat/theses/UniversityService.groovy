package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.dao.DataIntegrityViolationException

class UniversityService {

    def saveWithMemberships(University university, List<Membership> memberships) {
        def allMembers = university.users
        def difference = memberships.grep { !(it.user in allMembers) }

        def success = university.save() && difference.every { it.save() }
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    def deleteWithMemberships(University university, List<Membership> memberships) {
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
