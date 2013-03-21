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
        initConfig()
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

    def propertyMissing(String name) {
        initConfig()
        config.getProperty(name)
    }

    def propertyMissing(String name, value) {
        initConfig()
        config.setProperty(name, value)
    }

    private initConfig() {
        if (config == null) {
            config = configurationProvider.load()
            if (!config) {
                config = createDefaultConfig()
            }
        }
    }

    private ConfigObject createDefaultConfig() {
        Class configClass = getClass().classLoader.loadClass('DefaultRuntimeConfig')
        def config = new ConfigSlurper().parse(configClass)
        configurationProvider.save(config)
        config
    }
}
