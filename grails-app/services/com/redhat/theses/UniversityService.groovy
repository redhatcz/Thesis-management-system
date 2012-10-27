package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport

class UniversityService {

    def save(University university, List<Membership> memberships) {
        def allMembers = university.users
        def difference = memberships.grep { !(it.user in allMembers) }

        def success = university.save() && difference.every { it.save() }
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }
}
