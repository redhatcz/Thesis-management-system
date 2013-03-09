package com.redhat.theses

import com.redhat.theses.util.Util

/**
 * @author vdedik@redhat.com
 */
class ConfigurationController {

    static allowedMethods = [update: 'POST']

    /**
     * Dependency injection of com.redhat.theses.config.Configuration
     */
    def configuration

    def index() {
        if (Util.isActionInUrl(request, 'index')) {
            redirect uri: '/configuration', permanent: true
        }
        [config: configuration.getConfig()]
    }

    def update() {
        ConfigObject config = new ConfigObject()
        config.putAll(params.configuration ?: [])
        configuration.setConfig(config)

        if (!configuration.save()) {
            flash.message = message(code: 'config.not.updated')
            render view: 'index', model: [config: config]
        }

        flash.message = message(code: 'config.updated')

        redirect uri: '/configuration'
    }
}
