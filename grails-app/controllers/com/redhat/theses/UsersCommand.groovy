package com.redhat.theses

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.ListUtils
import com.redhat.theses.auth.User
import grails.validation.Validateable

@Validateable
class UsersCommand {

    static constraints = {
        users validator: { users ->
            !users.any { it.id == null && !it.fullName?.isEmpty() }
        }
    }

    List<User> users = ListUtils.lazyList(new ArrayList<User>(), FactoryUtils.instantiateFactory(User.class));
}
