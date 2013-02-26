def credentials = [
        host: System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST"),
        port: System.getenv("OPENSHIFT_POSTGRESQL_DB_PORT"),
        //TODO: Change these later
        username: "admin",
        password: "FI_TGbqAvNxP",
        name:"theses"
]

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            pooled = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            logSql = true
        }
    }
    test {
        dataSource {
            pooled = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            logSql = true
        }
    }
    production {
        dataSource {
            username = credentials.username
            password = credentials.password
            url = "jdbc:postgresql://${credentials.host}:${credentials.port}/${credentials.name}"
            driverClassName = "org.postgresql.Driver"
            dbCreate = "update"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
