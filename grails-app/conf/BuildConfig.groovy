grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // runtime 'mysql:mysql-connector-java:5.1.20'
        runtime 'postgresql:postgresql:8.4-702.jdbc4'

        compile 'org.apache.tika:tika-core:1.3'
        compile 'org.imgscalr:imgscalr-lib:4.2'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.0"
        runtime ":resources:1.1.6"


        // Uncomment these (or add new ones) to enable additional resources capabilities
        runtime ":zipped-resources:1.0"
        runtime ":cached-resources:1.0"
        runtime ":cache-headers:1.0.4"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"

        runtime ":database-migration:1.3.6"

        compile ':cache:1.0.0'
        compile ":mongodb:1.1.0.GA"
        compile ":platform-core:1.0.RC5"
        compile ":markdown:1.1.1"
        compile ":spring-security-core:1.2.7.3"
        compile ":mail:1.0.1"
        compile ":email-confirmation:2.0.8"
        compile ":lesscss-resources:1.3.1"
        compile ":hibernate-filter:0.3.2"
        compile ":feeds:1.5"
        //compile ":searchable:0.6.4"
    }
}
