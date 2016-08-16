package com.redhat.theses

import com.redhat.theses.Topic
import com.redhat.theses.auth.User
import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared
import spock.lang.Unroll

class FilterServiceIntegrationSpec extends IntegrationSpec {
    @Shared filterService = new FilterService()

    def setupSpec() {
    }

    def cleanup() {
    }

    @Unroll("Can filter #domain entries, with filter #parameters, should return #size entries")
    void "testing filter"() {
        expect:
            filterService.filter(parameters, domain)?.size() == size
            filterService.count(parameters, domain) == count
        where:
            parameters | domain | size | count
            [filter: ['title-lead-description': "think"], type:  ['title-lead-description': "ilike"]] | Topic | 2 | 2
            [filter: ['title-lead-description': "think"], type:  ['title-lead-description': "ilike"], offset: 1]  | Topic | 1 | 2
            [filter: ['title': "think"], type:  [title: "ilike"]] | Topic | 0 | 0
            [filter: ['email': "example"], type:  [email: "ilike"]] | User | 7 | 7
            [filter: ['email': "example", fullName: "example"], type:  [email: "ilike", fullName: "ilike"], max: 1] | User | 1 | 3
            [filter: ['email': "example", fullName: "example"], type:  [email: "ilike"]] | User | 0 | 0
    }
}
