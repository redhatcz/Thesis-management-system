package com.redhat.theses

import com.redhat.theses.util.Util
import org.springframework.context.i18n.LocaleContextHolder as LCH

class TermsOfUseController {

    /**
     * Dependency injection of com.redhat.theses.config.Configuration
     */
    def configuration

    def index() {

        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/terms-of-use', permanent: true
        }

        def locale =  LCH.locale.toString()
        def termsOfUse = configuration?."termsOfUse_$locale"

        if (!termsOfUse) {
            locale =  Locale.ENGLISH.toString()
            termsOfUse = configuration?."termsOfUse_$locale"
        }
        [termsOfUse: termsOfUse]
    }
}
