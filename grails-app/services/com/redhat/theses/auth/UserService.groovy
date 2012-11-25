package com.redhat.theses.auth

import org.springframework.transaction.interceptor.TransactionAspectSupport
import com.redhat.theses.Membership
import com.redhat.theses.Organization
import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.lang.RandomStringUtils
import com.redhat.theses.events.UserCreatedEvent

class UserService {

    Boolean saveWithOrganizations(User user, List<Organization> organizations) {
        String eventType = user.id ? 'update' : 'create'

        String password = user.password
        if (!password) {
            password = RandomStringUtils.random(8, true, true)
            user.password = password
        }

        def success = user.save()
        def persistedUser = success

        if (!success) {
            return success
        }

        def filteredOrganizations = organizations.unique().findAll {it}
        def allMembershipsOfUser = Membership.findAllByUser(persistedUser)
        def allOrganizationsOfUser = allMembershipsOfUser*.organization
        def membershipsToBeDeleted = allMembershipsOfUser.findAll {!(it.organization in filteredOrganizations)}
        def membershipsToBeSaved = filteredOrganizations.findAll {!(it in allOrganizationsOfUser)}
                .collect { new Membership(user: user, organization: it) }

        success = success &&
                membershipsToBeDeleted.every {delete(it)} &&
                membershipsToBeSaved.every {it.save()}
        if (!success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        } else {
            if (eventType == 'create') {
                publishEvent(new UserCreatedEvent(persistedUser, password))
            }
        }
        success
    }

    Boolean deleteWithMemberships(User user) {
        def success = Membership.findAllByUser(user).every { delete(it) } &&
                delete(user)
        if (!success && !TransactionAspectSupport.currentTransactionStatus().completed) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        success
    }

    private Boolean delete(entity) {
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
