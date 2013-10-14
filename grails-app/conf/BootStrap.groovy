import com.gmongo.GMongo
import com.redhat.theses.*
import com.redhat.theses.auth.Role
import com.redhat.theses.auth.User
import com.redhat.theses.events.ArticleEvent

class BootStrap {

    def grailsEvents

    /**
     * Dependency Injection of com.grailsrocks.emailconfirmation.EmailConfirmationService
     */
    def emailConfirmationService

    def init = { servletContext ->

        // Change emailConfirmation maxAge to one day instead of 30 days
        emailConfirmationService.maxAge = 1000*10L

        // Do not run this script if it has been already run
        def testUsers = User.findAll()
        if (testUsers != null && testUsers.size() != 0) {
            return
        }

        environments {
            production {
                def admin = new User(
                        email: 'admin@admin.com',
                        fullName: 'Change Me',
                        password: "admin",
                        enabled: true,
                        roles: [Role.ADMIN, Role.OWNER, Role.SUPERVISOR, Role.STUDENT]
                ).save(flush: true)

            }

            development {

                // UNIVERSITIES
                def muni = new University(name: 'Masaryk University', acronym: 'MUNI').save(flush: true)
                def vut = new University(name: 'Brno University of Technology', acronym: 'BUT').save(flush: true)

                // USERS
                def admin = new User(
                        email: 'admin@gmail.com',
                        fullName: 'Admin Admin',
                        password: "admin",
                        enabled: true,
                        roles: [Role.ADMIN, Role.OWNER, Role.SUPERVISOR, Role.STUDENT]
                ).save(flush: true)

                def person = new User(
                        email: 'person@gmail.com',
                        fullName: 'Person Person',
                        password: "person",
                        enabled: true,
                        roles: [Role.SUPERVISOR, Role.STUDENT]
                ).save(flush: true)

                def padmin = new User(
                        username: 'padmin',
                        fullName: 'Person Admin',
                        password: "padmin",
                        enabled: true,
                        email: 'padmin@gmail.com',
                        roles: [Role.SUPERVISOR, Role.STUDENT]
                ).save(flush: true)

                def vaclav = new User(
                        email: 'vaclav.dedik@gmail.com',
                        fullName: 'Vaclav Dedik',
                        password: "vaclav.dedik",
                        enabled: true,
                        roles: [Role.STUDENT]
                ).save(flush: true)
                def pavel = new User(
                        email: 'dedikx@gmail.com',
                        fullName: 'Pavel Dedik',
                        password: "dedikx",
                        enabled: true,
                        roles: [Role.STUDENT]
                ).save(flush: true)
                def kuba = new User(
                        email: 'jcechace@gmail.com',
                        fullName: 'Jakub Cechacek',
                        password: "jcechace",
                        enabled: true,
                        roles: [Role.STUDENT]
                ).save(flush: true)
                def jiriKolar = new User(
                        email: 'jiri.kolar@gmail.com',
                        fullName: 'Jiri Kolar',
                        password: "jiri.kolar",
                        enabled: true,
                        roles: [Role.SUPERVISOR, Role.STUDENT]
                ).save(flush: true)
                def jiriPechanec = new User(
                        email: 'jpechanec@redhat.com',
                        fullName: 'Jiri Pechanec',
                        password: "jpechanec",
                        enabled: true,
                        roles: [Role.OWNER, Role.SUPERVISOR, Role.STUDENT]
                ).save(flush: true)

                // TAGS
                def javaTag = new Tag(title: 'java').save(flush: true)
                def groovyTag = new Tag(title: 'groovy').save(flush: true)
                def pythonTag = new Tag(title: 'python').save(flush: true)
                def rubyTag = new Tag(title: 'ruby').save(flush: true)
                def othersTag = new Tag(title: 'others').save(flush: true)
                def wfkTag = new Tag(title: 'wfk').save(flush: true)
                def grailsTag = new Tag(title: 'grails').save(flush: true)
                def djangoTag = new Tag(title: 'django').save(flush: true)

                // CATEGORIES
                def redhatCategory = new Category(title: 'Red hat', description: 'Projects that concerns red hat products').save(flush: true)
                def jbossCategory = new Category(title: 'JBoss', description: 'Community projects').save(flush: true)
                def rhelCategory = new Category(title: 'RHEL', description: 'Red hat enterprise linux projects').save(flush: true)

                // TOPICS
                def tms = new Topic(
                        title: 'Thesis management system',
                        owner: jiriPechanec,
                        tags: [grailsTag],
                        categories: [redhatCategory, jbossCategory],
                        types: [Type.BACHELOR],
                        universities: [muni, vut],
                        lead: '''Application for managing theses for Red Hat''',
                        description:
'''The goal of this project is to create a Thesis management system for company Red Hat. This system will be implemented
using the most *state-of-the-art* technologies and will be deployed on OpenShift.
###Requirements

* Categories (or hierarchical tags)
* List of universities that the thesis is offered for
* Fulltext search
* Discussion on topics
* Application for topics
* Date of beginning of working on thesis

###Roles

1. Admin
2. Owner
3. Student
4. Supervisor
5. Anonymous (Guest)

_Note: You should also consider management of school projects._
'''
                ).save(flush: true)
                new Supervision(topic: tms, supervisor: jiriKolar, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(tms, tms.owner, [tms.owner]))

                def torquebox = new Topic(
                        title: 'Torquebox productization',
                        owner: admin,
                        tags: [javaTag, rubyTag, wfkTag],
                        categories: [redhatCategory],
                        universities: [muni, vut],
                        lead: '''TorqueBox is a new kind of Ruby application platform that supports popular technologies such
as Ruby on Rails and Sinatra, while extending the footprint of Ruby applications to include built-in support for
services such as messaging, scheduling, caching, and daemons.''',
                        description: '''We're *very excited* to announce the release of TorqueBox 2.3.0! We hit a couple of
unexpected delays with this release but hopefully the list of new features and bug fixes will make it worth the wait.
Download TorqueBox 2.3.0 (ZIP) Browse Getting Started Guide Browse HTML manual Browse JavaDocs Browse Gem RDocs Download
PDF manual Download ePub manual:

* Interested in hosting or giving a *TorqueBox* talk? See our speakers page.
* In August, I had the honor of presenting TorqueBox at two great conferences

We're as happy as a moth in a sweater factory to announce the immediate availability of TorqueBox 2.1.2! This is a
bug-fix only release and is a recommended upgrade for anyone running TorqueBox 2.1.0 or 2.1.1. Download TorqueBox
2.1.2 (ZIP) Browse Getting Started Guide Browse HTML manual Browse JavaDocs Browse Gem RDocs Download PDF manual
Download ePub manual Highlights of major......'''
                ).save(flush: true)
                new Supervision(topic: torquebox, supervisor: admin, university: muni).save(flush: true)
                new Supervision(topic: torquebox, supervisor: person, university: vut).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(torquebox, torquebox.owner, [torquebox.owner]))

                def pythonFramework = new Topic(
                        title: 'Framework for Python',
                        owner: jiriPechanec,
                        tags: [pythonTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''Python is a general-purpose, high-level programming language whose design philosophy
emphasizes code readability. Its syntax is said to be clear and expressive. Python has a large and comprehensive
standard library.''',
                        description: '''###History:
Python was conceived in the late 1980s[16] and its implementation was started in December 1989[17] by Guido van
Rossum at CWI in the Netherlands as a successor to the ABC language (itself inspired by SETL)[18] capable of exception
handling and interfacing with the Amoeba operating system.[1] Van Rossum is Python's principal author, and his
continuing central role in deciding the direction of Python is reflected in the title given to him by the Python
community, Benevolent Dictator for Life (BDFL).
Python 2.0 was released on 16 October 2000, with many major new features including a full garbage collector and
support for Unicode. However, the most important change was to the development process itself, with a shift to a more
transparent and community-backed process.[19]
Python 3.0 (also called Python 3000 or py3k), a major, backwards-incompatible release, was released on 3 December
2008[20] after a long period of testing. Many of its major features have been backported to the backwards-compatible
Python 2.6 and 2.7.[21]
Python has been awarded a TIOBE Programming Language of the Year award twice (in 2007 and 2010), which is given to the
language with the greatest growth in popularity over the course of a year, as measured by the TIOBE index.[22]'''
                ).save(flush: true)
                new Supervision(topic: pythonFramework, supervisor: admin, university: vut).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(pythonFramework, pythonFramework.owner, [pythonFramework.owner]))

                def testsForWFK = new Topic(
                        title: 'Tests for WFK',
                        owner: jiriPechanec,
                        tags: [wfkTag],
                        categories: [redhatCategory],
                        universities: [muni],
                        lead: '''All-in-one solution with enterprise-ready versions of popular open source frameworks''',
                        description:
                        '''The *JBoss® Web Framework Kit* makes it easier to build and maintain light and rich Java™
applications that use popular open source technologies. It's included in JBoss Enterprise Application Platform and is
available separately for JBoss Enterprise Web Server.
###Benefits and features:
Simplify your use of popular open source frameworks. Avoid integration and version conflicts. The JBoss Web Framework
Kit is a single solution that includes certified and integrated software—everything you need to build and maintain
simple web applications.'''
                ).save(flush: true)
                new Supervision(topic: testsForWFK, supervisor: jiriKolar, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(testsForWFK, testsForWFK.owner, [testsForWFK.owner]))

                def mavenThingy = new Topic(
                        title: 'Maven Thingy',
                        owner: jiriPechanec,
                        tags: [javaTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''Apache Maven is a software project management and comprehension tool. Based on the concept of
a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of
information.''',
                        description:
                        '''If you think that *Mave*n could help your project, you can find out more information about in
the "About Maven" section of the navigation. This includes an in-depth description of what Maven is, a list of some
of its main features, and a set of frequently asked questions about what Maven is.

###Learning about Maven
This site is separated into the following sections, depending on how you'd like to use Maven:

* Run maven
* Use maven
* Write maven plugins
* Improve maven

You can access the guides at any time from the left navigation.

###Documentation Index
If you are looking for a quick reference, you can use *the documentation* index.
'''
                ).save(flush: true)
                new Supervision(topic: mavenThingy, supervisor: jiriKolar, university: muni).save(flush: true)
                new Supervision(topic: mavenThingy, supervisor: admin, university: muni).save(flush: true)
                new Supervision(topic: mavenThingy, supervisor: person, university: vut).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(mavenThingy, mavenThingy.owner, [mavenThingy.owner]))

                def grailsPluginForLess = new Topic(
                        title: 'Grails plugin for less resources',
                        owner: admin,
                        tags: [grailsTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''This plugin supports server-side compilation of .less CSS files to their .css counterparts.''',
                        description:
                        '''This plugin is designed to optimise the use of .less css files. The Processing will compile
specified .less files into their .css counterparts, and place the css into the processing chain to be available to the
other resource plugin features. The plugin uses the Asual Less Engine to compile the .less files using Mozilla Rhino.
The plugin uses the Resources Plugin and plays nicely with both the zipped and cached resources plugins.
LESS extends CSS with dynamic behavior such as variables, mixins, operations and functions. Read more

    This plugin is seen as a replacement for the lesscss plugin . It utilizes the resources plugin which will provide
    a much more stable and future-proof platform. It is highly recommended that you use this version.'''
                ).save(flush: true)
                new Supervision(topic: grailsPluginForLess, supervisor: admin, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(grailsPluginForLess, grailsPluginForLess.owner, [grailsPluginForLess.owner]))


                def djangoPluginForLess = new Topic(
                        title: 'Django plugin for less resources',
                        owner: admin,
                        tags: [djangoTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''Django LESS provides template tags to compile LESS into CSS from templates. It works with both
inline code and extenal files.''',
                        description:
                        '''##Instalation

1. Add "less" to INSTALLED_APPS setting.
2. Make sure that you have lessc executable installed. See LESS official site for details.
3. Optionally, you can specify the full path to lessc executable with LESS_EXECUTABLE setting. By default it's set to lessc.
4. In case you use Django’s staticfiles contrib app you have to add django-less’s file finder to the STATICFILES_FINDERS setting, for example :

    STATICFILES_FINDERS = (
        'django.contrib.staticfiles.finders.FileSystemFinder',
        'django.contrib.staticfiles.finders.AppDirectoriesFinder',
        # other finders..
        'less.finders.LessFinder',
    )

##Example usage
###Inline

    {% load less %}

###External file

    {% load less %}

    <link rel="stylesheet" href="{{ STATIC_URL}}{% less "path/to/styles.less" %}" />

renders to

    <link rel="stylesheet" href="/media/LESS_CACHE/path/to/styles-91ce1f66f583.css" />

_Note: none_
'''
                ).save(flush: true)
                new Supervision(topic: djangoPluginForLess, supervisor: admin, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(djangoPluginForLess, djangoPluginForLess.owner, [djangoPluginForLess.owner]))

                def markdownCompiler = new Topic(
                        title: 'Markdown compiler in groovy',
                        owner: jiriPechanec,
                        tags: [othersTag, groovyTag],
                        categories: [jbossCategory],
                        universities: [muni],
                        lead: '''Markdown is a text-to-HTML conversion tool for web writers. Markdown allows you to write
using an easy-to-read, easy-to-write plain text format, then convert it to structurally valid XHTML (or HTML).''',
                        description:
                        '''Thus, “Markdown” is two things: (1) a plain text formatting syntax; and (2) a software
tool, written in _Perl, that converts the plain text formatting to HTML._ See the Syntax page for details pertaining
to Markdown’s formatting syntax. You can try it out, right now, using the online Dingus.

The overriding design goal for Markdown’s formatting syntax is to make it as readable as possible. The idea is that
a Markdown-formatted document should be publishable as-is, as plain text, without looking like it’s been marked up
with tags or formatting instructions. *While Markdown’s* syntax has been influenced by several existing text-to-HTML
filters, the single biggest source of inspiration for Markdown’s syntax is the format of plain text email.

The best way to get a feel for Markdown’s formatting syntax is simply to look at a Markdown-formatted document. For
example, you can view the Markdown source for the article text on this page here:
http://daringfireball.net/projects/markdown/index.text'''
                ).save(flush: true)
                new Supervision(topic: markdownCompiler, supervisor: jiriKolar, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(markdownCompiler, markdownCompiler.owner, [markdownCompiler.owner]))

                def jbossMavenPlugin = new Topic(
                        title: 'JBoss AS maven plugin',
                        owner: jiriPechanec,
                        tags: [javaTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''Jboss AS maven plugin for deployment, undeployment and redeployment of a Java EE application
on JBoss AS''',
                        description:
                        '''The jboss-as-maven-plugin is used to deploy, redeploy, undeploy or run your application.
You can also deploy or undeploy artifacts, such as JDBC drivers, and add or remove resources. There is also the
ability to execute CLI commands.

###Goals overview:

* jboss-as:add-resource adds a resource.
* jboss-as:deploy deploys the application to.
* jboss-as:deploy-artifact deploys an arbitrary artifact to the server.
* jboss-as:redeploy redeploys the application.
* jboss-as:undeploy undeploys the application.
* jboss-as:run runs the application server and deploys your application.
* jboss-as:execute-commands executes commands on the running server.

###Usage
General instructions on how to use the JBoss AS7 Deployment Plugin can be found on the usage page. Some more
specific use cases are described in the examples given below. Last but not least, users occasionally contribute
additional examples, tips or errata to the JBoss Application Server wiki page.

In case you still have questions regarding the plugin's usage, please have a look at the FAQ and feel free to
contact the user mailing list. The posts to the mailing list are archived and could already contain the answer
to your question as part of an older thread. Hence, it is also worth browsing/searching the mail archive.'''
                ).save(flush: true)
                new Supervision(topic: jbossMavenPlugin, supervisor: jiriKolar, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(jbossMavenPlugin, jbossMavenPlugin.owner, [jbossMavenPlugin.owner]))

                def twitterBootstrapPlugin = new Topic(
                        title: 'Twitter bootstrap plugin for picking date',
                        owner: admin,
                        tags: [othersTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''Add datepicker picker to field or to any other element.''',
                        description:
                        '''Fixed it to work with Boostrap version 2.1.1 and jQuery 1.8.1 Fixed current date highlight
Added the option to set the `viewMode` via data attribute Added a new option: 'minViewMode' Changed the navigation
images to HTML entities Fixed the parsing date Exposed a new method 'setDate' Adde a new property to the 'changeDate'
event: 'viewMode' Fixed transition from month to month, when the next month has less days then the previous

###Methods

    .datepicker(options)

Initializes an datepicker.

    .datepicker('show')

Show the datepicker.

    .datepicker('hide')

Hide the datepicker.

    .datepicker('place')

Updates the date picker's position relative to the element

    .datepicker('setValue', value)

Set a new value for the datepicker. It cand be a string in the specified format or a Date object.'''
                ).save(flush: true)
                new Supervision(topic: twitterBootstrapPlugin, supervisor: admin, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(twitterBootstrapPlugin, twitterBootstrapPlugin.owner, [twitterBootstrapPlugin.owner]))

                def autocompletePlugin = new Topic(
                        title: 'Autocomplete plugin for grails',
                        owner: jiriPechanec,
                        tags: [grailsTag],
                        categories: [jbossCategory],
                        universities: [muni, vut],
                        lead: '''Typeahead is a feature of computers and software (and some typewriters) that enables users
to continue typing regardless of program or computer operation—the user may type in whatever speed he or she desires,
and if the receiving software is busy at the time it will be called to handle this later. Often this means that
keystrokes entered will not be displayed on the screen immediately.''',
                        description:
                        '''Typeahead has its roots in the age of typewriters. The IBM Selectric typewriter, first
released in 1961, had a mechanical key lockout feature designed to smooth out typists' irregular keystrokes that,
to many users, felt like typeahead.

Achieving true typeahead requires maintaining a so-called "typeahead buffer"—a FIFO queue, for instance—whose role
it is to store a limited amount of keyboard input until it is called for. Installing such a buffer can be done at
both the hardware and the software levels; most modern operating systems, such as Unix, implement this using software,
calling kernel interrupts.

In some network operations, one might attempt to dispatch information over a network, regardless whether the receiving
program manages to keep up, using the recipient's typeahead functions. However, as this is far too reliant on the
specifications of the computer with which one is communicating, it is not often used.'''
                ).save(flush: true)
                new Supervision(topic: autocompletePlugin, supervisor: jiriKolar, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(autocompletePlugin, autocompletePlugin.owner, [autocompletePlugin.owner]))

                def hibernateProductization = new Topic(
                        title: 'Productization of hibernate',
                        owner: jiriPechanec,
                        tags: [],
                        categories: [redhatCategory],
                        universities: [muni],
                        lead: '''Hibernate is an object-relational mapping (ORM) library for the Java language, providing a
framework for mapping an object-oriented domain model to a traditional relational database. ''',
                        description:
                        '''Mapping Java classes to database tables is accomplished through the configuration of an
XML file or by using Java Annotations. When using an XML file, Hibernate can generate skeletal source code for the
persistence classes. This is unnecessary when annotations are used. Hibernate can use the XML file or the annotations
to maintain the database schema.

Facilities to arrange one-to-many and many-to-many relationships between classes are provided. In addition to managing
associations between objects, Hibernate can also manage reflexive associations where an object has a one-to-many
relationship with other instances of its own type.

Hibernate supports the mapping of custom value types. This makes the following scenarios possible:

* Overriding the default SQL type that Hibernate chooses when mapping a column to a property.
* Mapping Java Enum to columns as if they were regular properties.
* Mapping a single property to multiple columns.
'''
                ).save(flush: true)
                new Supervision(topic: hibernateProductization, supervisor: jiriKolar, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(hibernateProductization, hibernateProductization.owner, [hibernateProductization.owner]))

                def rubyKillingMachine = new Topic(
                        title: 'Killing machine for Ruby',
                        secondaryTitle: 'Zabíjecí stroj pro Ruby',
                        owner: jiriPechanec,
                        tags: [rubyTag, othersTag],
                        categories: [redhatCategory, jbossCategory],
                        universities: [muni, vut],
                        lead: '''Killing Machine (also known as Hell Bent for Leather) is the fifth studio album by British
heavy metal band Judas Priest. With its release in October 1978, the album pushed the band towards a more commercial
style; however, it still contained the dark lyrical themes of their previous albums. At about the same time, the band
members adopted their now-famous "leather-and-studs" image.''',
                        description:
                        '''##Different titles
Killing Machine was retitled Hell Bent for Leather for release in the US, because the US branch of Columbia/CBS did not
like the "murderous implications" of the album title and with "The Green Manalishi (With the Two-Pronged Crown)" an
early Fleetwood Mac cover, being added to the recording.''',
                        secondaryDescription:
                        '''##Jiné nadpisy
Zabíjeci stroj je pátým albem heavy metalové legendy Judas Priest a v USA se prodává pod názvem Hell Bent for Leather.
Deska vyšla 1978 u společnosti Columbia Records a produkci tentokrát do svých rukou vzal James Guthrie. V roce 2001
bylo remasterováno a přidány bonusy, stejně jako tomu bylo u dalších 11 řadových alb Judas Priest a dvou výběrů.
Mohli jste si jej opatřit samostatně, nebo jako Box Set všech 12 remasterovaných studiových alb.'''
                ).save(flush: true)
                new Supervision(topic: rubyKillingMachine, supervisor: jiriKolar, university: muni).save(flush: true)
                new Supervision(topic: rubyKillingMachine, supervisor: admin, university: muni).save(flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(rubyKillingMachine, rubyKillingMachine.owner, [rubyKillingMachine.owner]))

                // THESES
                def kubaThesis = new Thesis(
                        title: "Implementation of Thesis management system",
                        description: '''Create Topic, file upload for theses so that students can upload their work after
they are done with their thesis and application management.''',
                        assignee: kuba,
                        topic: tms,
                        supervisor: jiriKolar,
                        tags: [grailsTag, groovyTag],
                        status: Status.IN_PROGRESS,
                        university: muni,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(kubaThesis, tms.owner, [kuba, jiriKolar, tms.owner]))

                def vaclavThesis = new Thesis(
                        title: "Implementation of Thesis management system",
                        description: '''Create User, University management, news feed system so
that everyone can see news on the home page, subscription for Topic, Thesis or Project so that anyone who is interested
in one of those can follow it, mail service for sending these feeds to subscribers and overall configuration.''',
                        assignee: vaclav,
                        topic: tms,
                        supervisor: jiriKolar,
                        tags: [grailsTag, groovyTag],
                        status: Status.IN_PROGRESS,
                        university: muni,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(vaclavThesis, tms.owner, [vaclav, jiriKolar, tms.owner]))

                def pavelThesis = new Thesis(
                        title: "Design for Thesis management system",
                        description: '''Paint all graphic stuff that anyone can do and create views for the TMS.''',
                        assignee: pavel,
                        topic: tms,
                        supervisor: jiriKolar,
                        tags: [othersTag, groovyTag],
                        status: Status.IN_PROGRESS,
                        university: muni,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(pavelThesis, tms.owner, [pavel, jiriKolar, tms.owner]))

                def pavelThesis2 = new Thesis(
                        title: "Implementation of a python framework",
                        description: '''Python is a general-purpose, high-level programming language whose design philosophy
emphasizes code readability. Python's syntax allows for programmers to express concepts in fewer lines of code than
would be possible in languages such as C, and the language provides constructs intended to enable clear programs on
both a small and large scale.''',
                        assignee: pavel,
                        topic: pythonFramework,
                        supervisor: admin,
                        status: Status.FINISHED,
                        tags: [pythonTag],
                        grade: Grade.F,
                        thesisAbstract: '''The majority of Web frameworks are exclusively server-side technology, although,
with the increased prevalence of AJAX, some Web frameworks are beginning to include AJAX code that helps developers
with the particularly tricky task of programming (client-side) the user's browser. At the extreme end of the client-side
Web Frameworks is technology that can use the web browser as a full-blown application execution environment (a la gmail
for example): see Web Browser Programming for details.''',
                        university: muni,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(pavelThesis2, pythonFramework.owner, [pavel, admin, pythonFramework.owner]))

                def vaclavThesis2 = new Thesis(
                        title: "Implementation of Ruby Killing Machine",
                        description: '''Implementation of Ruby Killing Machine that will kill people that are filled in an HTML form.''',
                        assignee: vaclav,
                        topic: rubyKillingMachine,
                        supervisor: jiriKolar,
                        status: Status.FINISHED,
                        tags: [rubyTag],
                        grade: Grade.A,
                        thesisAbstract: '''Implementation of Ruby Killing Machine that will kill people that are filled in an HTML form.''',
                        university: muni,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)
                grailsEvents.event('app', 'articleCreated', new ArticleEvent(vaclavThesis2, rubyKillingMachine.owner, [vaclav, jiriKolar, rubyKillingMachine.owner]))

                // COMMENTS
                new Comment(content: 'That is cool', user: vaclav, article: tms).save(flush: true)
                new Comment(content: 'There is missing something', user: jiriPechanec, article: tms).save(flush: true)
                new Comment(content: 'Yes you are right', user: kuba, article: tms).save(flush: true)
                new Comment(content: 'I am a spammer and I don\'t even mind', user: kuba, article: tms).save(flush: true)
                new Comment(content: 'Generally, frameworks provide support for a number of activities such as interpreting requests (getting form parameters,\n' +
                        'handling cookies and sessions), producing responses (presenting data as HTML or in other formats), storing data\n' +
                        'persistently, and so on. Since a non-trivial Web application will require', user: kuba, article: tms).save(flush: true)
                new Comment(content: 'Spam once again', user: kuba, article: tms).save(flush: true)
                new Comment(content: 'Generally, frameworks provide support for a number of activities such as interpreting requests (getting form parameters,\n' +
                        'handling cookies and sessions), producing responses (presenting data as HTML or in other formats), storing data\n' +
                        'persistently, and so on. Since a non-trivial Web application will require a number of different kinds of abstractions,\n' +
                        'often stacked upon each other, those frameworks which attempt to provide a complete solution for applications are often\n' +
                        'known as full-stack frameworks in that they attempt to supply components for each layer in the stack.\'\'\'', user: jiriPechanec, article: tms).save(flush: true)
                new Comment(content: 'Nothing imported', user: jiriKolar, article: tms).save(flush: true)
                new Comment(content: 'Another absolutely useless comment', user: vaclav, article: tms).save(flush: true)
                new Comment(content: 'Empty comment', user: kuba, article: tms).save(flush: true)
                new Comment(content: '> The goal of this project is to create a Thesis management system for company Red Hat.', user: kuba, article: tms).save(flush: true)
                new Comment(content: '**** you', user: pavel, article: tms).save(flush: true)
                new Comment(content: '**** you too', user: kuba, article: tms).save(flush: true)
                new Comment(content: 'Ok', user: jiriKolar, article: tms).save(flush: true)

                new Comment(content: 'Thesis', user: vaclav, article: kubaThesis).save(flush: true)

                // APPLICATIONS
                new Application(applicant: vaclav, topic: autocompletePlugin,
                        university: muni, note: 'ahoj', type: Type.BACHELOR, status: AppStatus.PENDING).save(flush: true)

                // FAQ
                new Faq(question: 'What is the current status of OpenShift Online?',
                        answer: 'A free Developer Preview version of OpenShift Online has been available for the past year. ' +
                                'Based on requests from users for expanded capability, we are planning to expand the OpenShift Online ' +
                                'offering to provide users increased capacity, functionality and support. The information below provides' +
                                ' an overview of what we intend to offer when a paid OpenShift Online service becomes available. While the ' +
                                'details below provide our pricing plans, some of the information may be subject to change as we continue ' +
                                'to fine-tune the offering to address our customers needs.').save(flush: true)
                new Faq(question: 'What is Red Hat\'s plan with respect to OpenShift Online pricing?',
                        answer: 'OpenShift Online is being offered in a pricing model comprised of different tiers intended to fit your ' +
                                'organization\'s needs, whether you are a hobbyist, a fast growing startup or an enterprise. There is a ' +
                                'base monthly platform fee plus costs for additional gear usage.').save(flush: true)
                new Faq(question: 'What is a gear?',
                        answer: 'A gear is a container with a set of resources that allows users to run their applications. OpenShift ' +
                                'Online runs many gears on each virtual machine and dynamically distributes gears across them.').save(flush: true)
                new Faq(question: 'What is a Cartridge?',
                        answer: 'Cartridges are the containers that house the framework or components that can be used to create an application. ' +
                                'One or more cartridges run on each gear or the same cartridge can run on many gears for clustering or scaling. ' +
                                'We currently have catridges that are enabled for PHP, JBoss, Ruby, Perl & NodeJS. We also offer embedded cartridges ' +
                                'in support of MySQL, MongoDB, phpmyadmin.').save(flush: true)
                new Faq(question: 'How are applications, cartridges and gears related?',
                        answer: 'Applications are made up of at least one framework that is contained in a cartridge and runs on one or more gears. ' +
                                'Additional cartridges can be added to the application on the same or different gears.').save(flush: true)
                new Faq(question: 'Are there different gear sizes and how much do they cost?',
                        answer: 'We plan to offer small gears for FreeShift and small and medium gears for MegaShift. ' +
                                'In the future we may offer larger gears at other tiers.').save(flush: true)
                new Faq(question: 'What is FreeShift?',
                        answer: 'FreeShift is the name of the free tier that is offered on OpenShift Online.')
                new Faq(question: 'What is MegaShift?',
                        answer: 'We plan to offer MegaShift as a paid tier on OpenShift Online that provides larger capacity.' +
                                ' Please see the pricing page for more information.').save(flush: true)

                def cs = new Locale('cs')
                new Faq(locale: cs,
                        question: 'Soubor se mi nestáhl do konce, můžu stahování nějak napojit, nebo musím stahovat znovu?',
                        answer: 'V současné době nelze napojovat částečně stažené soubory, je tedy nutné soubor stáhnout znovu. ' +
                                'Přehrávače pro systém Windows většinou nejsou schopny částečně stažené soubory přehrát.').save(flush: true)
                new Faq(locale: cs,
                        question: 'Při pokusu o stažení se mi zobrazí text: Neautorizovaný přístup.',
                        answer: 'Pokoušíte se o stažení video souboru jinou cestou než pres IS MU. ' +
                                'Je nutné přistupovat přes stránky univerzitního informačního systému.').save(flush: true)
                new Faq(locale: cs,
                        question: 'Při pokusu o stažení se mi zobrazí text: Chyba databáze.',
                        answer: 'Jde o technickou závadu v systému, kterou se snažíme ihned řešit, ' +
                                'zkuste Váš požadavek za chvíli znovu nebo se obraťte na kontaktní adresu: support@video.muni.cz.').save(flush: true)

            }
        }
    }

    def destroy = {
        environments {
            development {
                // drop database
                def mongo = new GMongo()
                def db = mongo.getDB('tms')
                db.dropDatabase()
            }
        }
    }
}
