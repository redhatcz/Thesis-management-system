package com.redhat.theses

import org.apache.commons.collections.ListUtils
import org.apache.commons.collections.FactoryUtils
import grails.validation.Validateable

@Validateable
class ConfigurationCommand {

    List<String> domains = new ArrayList<String>()
    List<String> defaultSupervisors = new ArrayList<String>()
    List<String> defaultLeaders = new ArrayList<String>()
    List<String> defaultAdmins = new ArrayList<String>()
        
    static constraints = {
        domains validator: validateDomains
        defaultSupervisors validator: validateDomains
        defaultLeaders validator: validateDomains
        defaultAdmins validator: validateDomains
    }
    
    static validateDomains = {list, object->
        if (list.any {(it =~ /[^a-zA-Z0-9-\\*\\.]+/).find()}) {
            'invalid'
        }
    }  
}
