package com.redhat.theses.config

/**
 * @author vdedik@redhat.com
 */
public interface ConfigurationProvider {

    /**
     * Loads configuration, somehow from somewhere :)
     *
     * @return ConfigObject which represents groovy configuration
     */
    ConfigObject load()

    /**
     * Save configuration, somehow somewhere :)
     *
     * @param config - ConfigObject to be serialized and saved
     */
    def save(ConfigObject config)
}