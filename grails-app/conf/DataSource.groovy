import org.grails.plugin.hibernate.filter.HibernateFilterDomainConfiguration

def credentials = [
        host: System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST"),
        port: System.getenv("OPENSHIFT_POSTGRESQL_DB_PORT"),
        username: System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME"),
        password: System.getenv("OPENSHIFT_POSTGRESQL_DB_PASSWORD"),
        name: System.getenv("OPENSHIFT_APP_NAME")
]

def mongoCredentials = [
        host: System.getenv("OPENSHIFT_MONGODB_DB_HOST"),
        port: System.getenv("OPENSHIFT_MONGODB_DB_PORT"),
        username: System.getenv("OPENSHIFT_MONGODB_DB_USERNAME"),
        password: System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD"),
        name: System.getenv("OPENSHIFT_APP_NAME")
]

dataSource {
    pooled = true
    configClass = HibernateFilterDomainConfiguration
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }

        grails {
            mongo {
                databaseName = 'thesis-management'
            }
        }
    }
    test {
        dataSource {
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }

        grails {
            mongo {
                databaseName = 'thesis-management'
            }
        }
    }
    production {
        dataSource {
            username = credentials.username
            password = credentials.password
            url = "jdbc:postgresql://${credentials.host}:${credentials.port}/${credentials.name}"
            driverClassName = "org.postgresql.Driver"
            properties {
               maxActive = 100
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }

        grails {
            mongo {
                host = mongoCredentials.host
                port = mongoCredentials.port
                username = mongoCredentials.username
                password = mongoCredentials.password
                databaseName = mongoCredentials.name
            }
        }
    }
}