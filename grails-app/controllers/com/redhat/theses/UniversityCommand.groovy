package com.redhat.theses

import grails.validation.Validateable
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.ListUtils

/**
 * @author vdedik@redhat.com
 */
@Validateable
class UniversityCommand {

    List<University> universities = ListUtils.lazyList(new ArrayList<University>(), FactoryUtils.instantiateFactory(University.class));
}
