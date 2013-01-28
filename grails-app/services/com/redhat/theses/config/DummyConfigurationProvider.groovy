package com.redhat.theses.config

/**
 * @author vdedik@redhat.com
 */
class DummyConfigurationProvider implements ConfigurationProvider {

    def configObject = new ConfigObject()

    DummyConfigurationProvider() {
        configObject.putAll([
                emailDomains: [
                        'mail.muni.cz',
                        'fit.vutbr.cz'
                ]
        ])
    }

    @Override
    ConfigObject load() {
        return configObject
    }

    @Override
    def save(ConfigObject config) {
        configObject = config
    }
}
