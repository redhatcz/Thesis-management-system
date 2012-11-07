package com.redhat.theses.auth

import org.springframework.transaction.interceptor.TransactionAspectSupport
import com.redhat.theses.Membership

class UserService {

    Boolean saveWithMemberships(User user, List<Membership> memberships) {
        def success = user.save() && memberships.every { it.save() }
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }
}
