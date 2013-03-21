package com.redhat.theses.config
import com.mongodb.BasicDBObject
/**
 * @author vdedik@redhat.com
 */
class MongoConfigurationProviderService implements ConfigurationProvider {

    private static String DEFAULT_DB = "configuration"

    private static String ID = "configurationId"

    /**
     * Dependency Injection of org.codehaus.groovy.grails.commons.GrailsApplication
     */
    def grailsApplication

    /**
     * Dependency Injection of com.mongodb.Mongo
     */
    def mongo

    private getDB() {
        def dbName = grailsApplication.config.grails.mongo.databaseName
        if (!dbName) {
            dbName = DEFAULT_DB
        }

        mongo.getDB(dbName)
    }

    @Override
    ConfigObject load() {
        def config = getDB().config.findOne(ID)
        if (config?.content) {
            config.content
        } else {
            null
        }
    }

    @Override
    def save(ConfigObject config) {
        def backup = getDB().config.findOne(ID)
        getDB().config.remove([_id: ID])

        def object = [_id: ID, content: config] as BasicDBObject
        getDB().config.insert(object)

        if (!getDB().config.findOne()) {
            getDB().config.insert(backup)
        }
    }
}
