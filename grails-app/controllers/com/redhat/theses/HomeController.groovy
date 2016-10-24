package com.redhat.theses

import com.redhat.theses.util.Util
import org.springframework.context.i18n.LocaleContextHolder as LCH

class HomeController {

    static allowedMethods = [index: 'GET']

    /**
     * Dependency injection of com.redhat.theses.config.Configuration
     */
    def configuration

    def index() {
        if (Util.isControllerOrActionInUrl(request, 'home', 'index')) {
            redirect uri: '/', permanent: true, params: params
        }

        def locale =  LCH.locale.toString()

        def defaultConfig = configuration.createDefaultConfig()

        def upper = configuration."upperIndex_$locale" ?: defaultConfig."upperIndex_$locale"
        def lower = configuration."lowerIndex_$locale" ?: defaultConfig."lowerIndex_$locale"

        render view: '/index', model: [config: configuration.getConfig(), upper: upper, lower: lower]
    }
}
