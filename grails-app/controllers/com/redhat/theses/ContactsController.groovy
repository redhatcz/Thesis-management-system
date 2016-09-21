package com.redhat.theses

import com.redhat.theses.util.Util
import org.springframework.context.i18n.LocaleContextHolder as LCH

class ContactsController {

    static allowedMethods = [index: 'GET']

    /**
     * Dependency injection of com.redhat.theses.config.Configuration
     */
    def configuration

    def index() {
        def locale =  LCH.locale.toString()

        def defaultConfig = configuration.createDefaultConfig()

        def contacts = configuration."contacts_$locale" ?: defaultConfig."contacts_$locale"

        [config: configuration.getConfig(), contacts: contacts]
    }
}
