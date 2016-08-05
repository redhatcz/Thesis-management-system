package com.redhat.theses.util

import com.redhat.theses.util.Util
import spock.lang.Specification
import spock.lang.Unroll

class UtilSpecification extends Specification {
    
    def setupSpec() {
    }

    def cleanup() {        
    }
    
    @Unroll("Will allow email #email through filter #domain, expected #result")
    void "test email filter"() {
        expect:
            Util.hasDomain(email, domain) == result
        where:
            email | domain | result
            "test@test.redhat.com" | "*.redhat.com" | true
            "test@fit.vutbr.cz" | "*vutbr.cz" | true
            "@fit.vutbr.cz" | "*vutbr.cz" | true
            "test@testuredhat.com"| "*.redhat.com" | false
            "test@fitvutbr.cz" | "*vutbr.cz" | false
            "test@xyz" | "vutbr.cz" | false
            "test@fit.vutbr.cz" | "vutbr.cz" | false
    }
}
