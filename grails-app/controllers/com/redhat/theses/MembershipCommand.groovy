package com.redhat.theses

import org.apache.commons.collections.ListUtils
import org.apache.commons.collections.FactoryUtils

/**
 * Created with IntelliJ IDEA.
 * User: jcechace
 * Date: 10/13/12
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
class MembershipCommand {
    List<Membership> memberships = ListUtils.lazyList(new ArrayList<Membership>(), FactoryUtils.instantiateFactory(Membership.class));
}
