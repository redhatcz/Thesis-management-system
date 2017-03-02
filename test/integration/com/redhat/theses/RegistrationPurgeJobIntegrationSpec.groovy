package com.redhat.theses

import com.redhat.theses.RegistrationPurgeJob
import com.redhat.theses.auth.User

import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared
import spock.lang.Unroll

class RegistrationPurgeJobIntegrationSpec extends IntegrationSpec {
    @Shared job = new RegistrationPurgeJob()

    void "All unconfirmed registration older than 24h will be deleted"() {
        given:
            def originalCount = User.count()
        when: "Executing job"
            job.execute()
            def newCount = User.count()
        then: "There should be less Users now (exactly 9)"
            originalCount != newCount 
            newCount == 9
    }

}
