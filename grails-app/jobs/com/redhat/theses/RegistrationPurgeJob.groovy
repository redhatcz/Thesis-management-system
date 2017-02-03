package com.redhat.theses

import com.redhat.theses.auth.User;
import com.redhat.theses.auth.UserService
import org.apache.commons.logging.LogFactory;
import groovy.util.logging.Log4j;
import org.apache.log4j.Logger;

class RegistrationPurgeJob {
    // Couldn't use DI because it doesn't work when testing
    def userService = new UserService()

    static triggers = {
        cron name: 'cronTrigger', cronExpression: '0 0 * 1/1 * ? *'
    }

    def execute() {
        User.findAllNotEnabledByDateCreatedLessThan(new Date().previous()).each {
            log.info "Removing " + it.email + "'s registration, because it is older than 24h and still unconfirmed."
            userService.delete(it);
        }
    }
}
