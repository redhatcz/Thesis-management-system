package com.redhat.theses

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.ListUtils
import com.redhat.theses.auth.User


class UsersCommand {

    List<User> users = ListUtils.lazyList(new ArrayList<User>(), FactoryUtils.instantiateFactory(User.class));
}
