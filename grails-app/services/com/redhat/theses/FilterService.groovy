package com.redhat.theses

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder
import org.codehaus.groovy.grails.web.converters.ConverterUtil

/**
 * @author vdedik@redhat.com
 */
class FilterService {

    /**
     * Dependency Injection of org.codehaus.groovy.grails.commons.GrailsApplication
     */
    def grailsApplication

    /**
     * Filters domain objects of filterClass
     *
     * @param params - params, pagination params and filter params prefixed by filter.
     * @param filterClass - domain class type, e.g. Topic
     * @return the result
     */
    def filter(Map params, Class filterClass) {
        filterClass.withoutHibernateFilters{
            return filterParams(params, filterClass, false)
        }
    }

    /**
     * Returns number of all filtered objects
     */
    def count(Map params, Class filterClass) {
        filterClass.withoutHibernateFilters{
            return filterParams(params, filterClass, true)
        }
    }

    private filterParse(c, domainClass, params, filterParams, filterTypeParams, doCount) {
        // First pull out the op map and store a list of its keys.
        def keyList = []
        keyList.addAll(filterParams.keySet())
        keyList = keyList.sort() // Sort them to get nested properties next to each other.

        keyList.each() { propName ->

            // Skip associated property entries.  (They'll have a dot in them.)  We'll use the map instead later.
            // And skip all password properties for security purposes
            if (! propName.contains(".") && ! propName.contains("password")) {

                def rawValue = filterParams[propName]
                def rawType = filterTypeParams ? filterTypeParams[propName] : null

                // If the filterOp is a Map, then the propName is an association (e.g. Book.author)
                if (rawValue instanceof Map) {
                    def nextFilterParams = rawValue
                    def nextFilterTypeParams = rawType

                    // Are any of the values non-empty?
                    if (nextFilterParams.values().find {it.size() > 0} != null) {
                        def nextDomainProp = resolveDomainProperty(domainClass, propName)

                        if (nextDomainProp) {
                            c."${propName}"() {

                                def nextDomainClass = nextDomainProp.referencedDomainClass

                                filterParse(c, nextDomainClass, params,
                                        nextFilterParams, nextFilterTypeParams, doCount)

                                // If they want to sort by an associated property, need to do it here.
                                if (!doCount && params.sort && params.sort.startsWith("${propName}.")) {
                                    def parts = params.sort.split("\\.")
                                    if (parts.size() == 2) {
                                        order(parts[1], params.order ?: 'asc')
                                    }
                                }
                            }
                        } else {
                            def extDomainClassName = propName.capitalize()
                            def extDomainClass = grailsApplication.domainClasses.find {
                                it.clazz.simpleName == extDomainClassName || it.clazz.simpleName == extDomainClassName[0..-2]
                            }

                            if (extDomainClass) {
                                def extProjectionClassName = domainClass.clazz.simpleName.toLowerCase()
                                def extC = extDomainClass.clazz.createCriteria()
                                def extCriteriaClosure = {
                                    extC.projections {
                                        "${extProjectionClassName}" {
                                            property('id')
                                        }
                                    }

                                    and {
                                        filterParse(extC, extDomainClass, [], filterParams[propName],
                                                filterTypeParams[propName] ?: [], false)
                                    }
                                }
                                def extObjectIds = extC.list(extCriteriaClosure).unique()

                                if (extObjectIds) {
                                    c.in('id', extObjectIds)
                                } else {
                                    //Postgres throws error when empty set is here
                                    c.in('id', [-1L] as Set)
                                }
                            }

                        }
                    }
                } else {
                    def thisDomainProp = resolveDomainProperty(domainClass, propName)

                    if (thisDomainProp) {
                        def val = this.parseValue(thisDomainProp, rawValue)
                        this.addCriterion(c, propName, val, rawType)
                    }
                }
            }
        }
    }

    private filterParams(Map params, Class filterClass, boolean doCount) {
        def filterParams = params.filter
        def filterTypeParams = params.type

        def domainClass = resolveDomainClass(filterClass)

        if (filterParams != null && filterParams.size() > 0) {

            def c = filterClass.createCriteria()

            def criteriaClosure = {
                and {
                    filterParse(c, domainClass, params, filterParams, filterTypeParams, doCount)
                }

                if (doCount) {
                    c.projections {
                        rowCount()
                    }
                } else {
                    if (params.offset) {
                        firstResult(params.offset.toInteger())
                    }
                    if (params.max) {
                        maxResults(params.max.toInteger())
                    }
                    if (params.fetchMode) {
                        def fetchModes = null
                        if (params.fetchMode instanceof Map)
                            fetchModes = params.fetchModes

                        if (fetchModes) {
                            fetchModes.each { association, mode ->
                                c.fetchMode(association, mode)
                            }
                        }
                    }

                    def defaultSort = GrailsDomainBinder.getMapping(filterClass)?.sort
                    def defaultOrder = GrailsDomainBinder.getMapping(filterClass)?.order
                    if (params.sort) {
                        if (params.sort.indexOf('.') < 0) {
                            order(params.sort, params.order ?: 'asc' )
                        }
                    } else if (defaultSort != null) {
                        order(defaultSort, defaultOrder ?: 'asc')
                    }
                }
            }

            def results = null
            if (doCount) {
                try {
                    results = c.get(criteriaClosure)
                } catch (ex) {
                    results = filterClass.count()
                }
            } else {
                try {
                    results = c.list(criteriaClosure)
                } catch (ex) {
                    results = filterClass.list(params)
                }
            }
            if (doCount && results instanceof List) {
                results = 0I
            }
            return results
        } else {
            // If no valid filters were submitting, run a count or list.  (Unfiltered data)
            if (doCount) {
                return filterClass.count()//0I
            }
            return filterClass.list(params)
        }
    }

    private addCriterion(criteria, propertyName, value, type) {
        boolean added = true

        if(value != null) {
            if (value instanceof String && type == 'ilike') {
                criteria.ilike(propertyName, "%${value}%")
            } else if (value instanceof Object[] || value instanceof Collection) {
                criteria.in(propertyName, value)
            } else {
                criteria.eq(propertyName, value)
            }
        } else {
            added = false
        }

        added
    }

    /**
     * Parse the user input value to the domain property type.
     * @returns The input parsed to the appropriate type if possible, else null.
     */
    def parseValue(domainProperty, val) {
        if (val instanceof Object[] || val instanceof Collection) {
            return val.collect {parseSingleValue(domainProperty, it)}
        } else {
            return parseSingleValue(domainProperty, val)
        }
    }

    private parseSingleValue(domainProperty, val) {
        if(val instanceof String) {
            val = val.trim()
        }

        if (val != null) {
            Class cls = domainProperty.referencedPropertyType ?: domainProperty.type
            String clsName = cls.simpleName.toLowerCase()

            if (cls.isEnum()) {
                def tempVal = val
                val = null // default to null.  If it's valid, it'll get replaced with the real value.
                try {
                    if (tempVal.toString().length() > 0) {
                        val = Enum.valueOf(cls, tempVal.toString())
                    }
                } catch(IllegalArgumentException iae) {
                    log.debug("Enum valueOf failed.  val is ${tempVal}")
                    // Ignore this.  val is not a valid enum value (probably an empty string).
                }
            } else if ("boolean".equals(clsName)) {
                if (val.toBoolean() || val == 'on') {
                    val = true
                } else {
                    val = false
                }
            } else if ( "int".equals(clsName) || "integer".equals(clsName) ) {
                val = val.isInteger() ? val.toInteger() : null
            } else if ("long".equals(clsName)) {
                try { val = val.toLong() } //no isShort()
                catch(java.lang.NumberFormatException e) { val = null }
            } else if ("double".equals(clsName)) {
                val = val.isDouble() ? val.toDouble() : null
            } else if ("float".equals(clsName)) {
                val = val.isFloat() ? val.toFloat() : null
            } else if ("short".equals(clsName)) {
                try { val = val.toShort() } //no isShort()
                catch(java.lang.NumberFormatException e) { val = null }
            }
        }
        return val
    }

    private resolveDomainClass(bean) {
        if(bean instanceof GrailsDomainClass) {
            return bean
        }
        String beanName = null
        if (bean instanceof Class) {
            beanName = bean.name
        } else if(bean instanceof String) {
            beanName = bean
        }
        if (beanName) {
            def result = grailsApplication.getDomainClass(beanName)
            if (result == null)
                result = ConverterUtil.getDomainClass(beanName)
            return result
        }
        return null
    }

    private resolveDomainProperty(domainClass, property) {

        if ("id".equals(property) || "identifier".equals(property))
            return domainClass.identifier

        def thisDomainProp = domainClass.persistentProperties.find {
            it.name == property
        }
        return thisDomainProp
    }
}