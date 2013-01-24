package com.redhat.theses

import com.redhat.theses.util.Commons
import org.springframework.transaction.interceptor.TransactionAspectSupport

class OrganizationService {

    Boolean deleteWithMemberships(Organization organization) {
        def memberships = Membership.findAllByOrganization(organization)
        def success = memberships.each { Commons.delete(it) } && Commons.delete(organization)
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }
}
