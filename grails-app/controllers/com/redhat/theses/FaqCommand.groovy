package com.redhat.theses

import grails.validation.Validateable
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.ListUtils

@Validateable
class FaqCommand {

    List<Faq> faq = ListUtils.lazyList(new ArrayList<Faq>(), FactoryUtils.instantiateFactory(Faq.class));

    static constraints = {
        faq validator: { faq ->
            if (!faq.inject(true) { acc, q -> q?.validate() && acc }) {
                'error.many'
            }
        }
    }
}