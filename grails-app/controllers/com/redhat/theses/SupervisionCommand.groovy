package com.redhat.theses

import org.apache.commons.collections.ListUtils
import org.apache.commons.collections.FactoryUtils
import grails.validation.Validateable

@Validateable
class SupervisionCommand {

    List<Supervision> supervisions = ListUtils.lazyList(new ArrayList<Supervision>(), FactoryUtils.instantiateFactory(Supervision.class));
}