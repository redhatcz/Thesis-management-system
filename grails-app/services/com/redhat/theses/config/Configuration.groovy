package com.redhat.theses.config

/**
 * @author vdedik@redhat.com
 */
class Configuration {

    /**
     * Dependency injection of com.redhat.theses.config.ConfigurationProvider (set up in resources.groovy)
     */
    def configurationProvider

    ConfigObject config = null

    ConfigObject getConfig() {
        if (config == null) {
            config = configurationProvider.load()
            if (!config) {
                config = createDefaultConfig()
            }
        }
        config
    }

    def setConfig(ConfigObject config) {
        this.config = config
    }

    def save() {
        if (config != null) {
            configurationProvider.save(config)
        } else {
            false
        }
    }

    private ConfigObject createDefaultConfig() {
        Class configClass = getClass().classLoader.loadClass('DefaultRuntimeConfig')
        def config = new ConfigSlurper().parse(configClass)
        configurationProvider.save(config)
        config
    }

    def propertyMissing(String name) {
        if (config == null) {
            config = configurationProvider.load()
        }
        config.getProperty(name)
    }

    def propertyMissing(String name, value) {
        if (config == null) {
            config = configurationProvider.load()
        }
        config.setProperty(name, value)
    }
}
