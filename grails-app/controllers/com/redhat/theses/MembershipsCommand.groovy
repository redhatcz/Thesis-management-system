package com.redhat.theses

import org.apache.commons.collections.ListUtils
import org.apache.commons.collections.FactoryUtils
import grails.validation.Validateable

@Validateable
class MembershipsCommand {

    static constraints = {
        memberships validator: {memberships ->
            memberships.every { Membership.get(it?.id) }
        }
    }

    List<Membership> memberships = ListUtils.lazyList(new ArrayList<Membership>(), FactoryUtils.instantiateFactory(Membership.class));
}
