// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true

        grails {
            mail {
                host = "smtp.gmail.com"
                port = 465
                username = "noone.localhost@gmail.com"
                password = "noone.local"
                overrideAddress = "noone.localhost@gmail.com"
                props = ["mail.smtp.auth":"true",
                        "mail.smtp.socketFactory.port":"465",
                        "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                        "mail.smtp.socketFactory.fallback":"false"]
            }
        }
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "https://${System.getenv('OPENSHIFT_APP_DNS')}"

        grails.mail.default.from="noreply@thesis.redhat.com"
        grails {
            mail {
                host = System.getenv('OPENSHIFT_EMAIL_HOST')
                port = System.getenv('OPENSHIFT_EMAIL_PORT')?.toInteger()
                username = System.getenv('OPENSHIFT_EMAIL_USERNAME')
                password = System.getenv('OPENSHIFT_EMAIL_PASSWORD')
                props = ["mail.smtp.auth":"true",
                        "mail.smtp.socketFactory.port":"465",
                        "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                        "mail.smtp.socketFactory.fallback":"false"]
            }
        }
    }
}

// log4j configuration
log4j = {

    environments {
        development {
            appenders {
                file name: 'sqlAppender', file: '/tmp/logs/tms-sql.log'
            }

            error 'org.codehaus.groovy.grails.web.servlet',        // controllers
                  'org.codehaus.groovy.grails.web.pages',          // GSP
                  'org.codehaus.groovy.grails.web.sitemesh',       // layouts
                  'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                  'org.codehaus.groovy.grails.web.mapping',        // URL mapping
                  'org.codehaus.groovy.grails.commons',            // core / classloading
                  'org.codehaus.groovy.grails.plugins',            // plugins
                  'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
                  'org.springframework',
                  'org.hibernate',
                  'net.sf.ehcache.hibernate'

            debug sqlAppender: ['org.hibernate.SQL'], additivity: false

            debug 'grails.app.controllers.com.redhat',
                  'grails.app.domain.your.com.redhat',
                  'grails.app.services.com.redhat',
                  'grails.app.taglib.com.redhat',
                  'grails.app.conf.com.redhat',
                  'grails.app.filters.com.redhat'
        }

        production {
            appenders {
                rollingFile name: "jbossews", file: "${System.getenv('OPENSHIFT_JBOSSEWS_LOG_DIR')}/tms.log"
            }

            error jbossews: ['org.codehaus.groovy.grails.web.servlet',        // controllers
                             'org.codehaus.groovy.grails.web.pages',          // GSP
                             'org.codehaus.groovy.grails.web.sitemesh',       // layouts
                             'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                             'org.codehaus.groovy.grails.web.mapping',        // URL mapping
                             'org.codehaus.groovy.grails.commons',            // core / classloading
                             'org.codehaus.groovy.grails.plugins',            // plugins
                             'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
                             'org.springframework',
                             'org.hibernate',
                             'net.sf.ehcache.hibernate']

            info jbossews: ['grails.app.controllers.com.redhat',
                            'grails.app.domain.your.com.redhat',
                            'grails.app.services.com.redhat',
                            'grails.app.taglib.com.redhat',
                            'grails.app.conf.com.redhat',
                            'grails.app.filters.com.redhat']
        }
    }
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.redhat.theses.auth.User'
grails.plugins.springsecurity.authority.className = 'com.redhat.theses.auth.Role'
environments {
    production {
        // Require https in production
        grails.plugins.springsecurity.secureChannel.definition = [
                '/**': 'REQUIRES_SECURE_CHANNEL'
        ]
        grails.plugins.springsecurity.portMapper.httpPort = 80
        grails.plugins.springsecurity.portMapper.httpsPort = 443
    }
}

// fix for g:paginate to work with bootstrap
grails.plugins.twitterbootstrap.fixtaglib = true

// use hyphenated url
grails.web.url.converter = 'hyphenated'

// remove html from markdown
markdown.removeHtml = true
// allow hardwraps etc
markdown.all = true

// database migration configuration
environments {
    production {
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
    }
}
