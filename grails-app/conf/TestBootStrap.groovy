import com.gmongo.GMongo
import com.redhat.theses.*
import com.redhat.theses.auth.Role
import com.redhat.theses.auth.User
import com.redhat.theses.events.ArticleEvent

class TestBootStrap {

    def grailsEvents

    def init = { servletContext ->

        environments {
            
            test {
                // UNIVERSITIES
                def first = new University(name: "First" ,acronym: "F").save(flush: true)
                def second = new University(name: "Second",acronym: "S").save(flush: true)
                def unused = new University(name: "Third",acronym: "T").save(flush: true)

                // USERS
                def admin = new User(
                        email: 'admin@example.com',
                        fullName: 'Admin Admin',
                        password: "admin",
                        enabled: true,
                        roles: [
                            Role.ADMIN,
                            Role.OWNER,
                            Role.SUPERVISOR,
                            Role.STUDENT]
                ).save(flush: true)

                def person = new User(
                        email: 'person@gmail.com',
                        fullName: 'Person Person',
                        password: "person",
                        enabled: true,
                        roles: [
                            Role.SUPERVISOR,
                            Role.STUDENT]
                ).save(flush: true)

                def supervisor1 = new User(
                        email: 'supervisor1@example.com',
                        fullName: 'Supervisor Jedna',
                        password: "supervisor1",
                        enabled: true,
                        roles: [
                            Role.SUPERVISOR,
                            Role.STUDENT]
                ).save(flush: true)

                def supervisor2 = new User(
                        email: 'supervisor2@example.com',
                        fullName: 'Second Supervisor',
                        password: "supervisor2",
                        enabled: true,
                        roles: [
                            Role.SUPERVISOR,
                            Role.STUDENT]
                ).save(flush: true)

                def example1 = new User(
                        email: 'example1@example.com',
                        fullName: 'Example Jedna',
                        password: "example1",
                        enabled: true,
                        roles: [Role.STUDENT]).save(flush: true)

                def example2 = new User(
                        email: 'example2@example.com',
                        fullName: 'Example Dva',
                        password: "example2",
                        enabled: true,
                        roles: [Role.STUDENT]).save(flush: true)

                def example3 = new User(
                        email: 'example3@example.com',
                        fullName: 'Example Tri',
                        password: "example3",
                        enabled: true,
                        roles: [Role.STUDENT]).save(flush: true)
                        
                def owner1 = new User(
                        email: 'owner1@example.com',
                        fullName: 'Owner Jedna',
                        password: "owner2",
                        enabled: true,
                        roles: [
                             Role.OWNER,
                             Role.SUPERVISOR,
                             Role.STUDENT]
                ).save(flush: true)
                //TAGS
                def grailsTag = new Tag(title: 'grails').save(flush: true)
                def djangoTag = new Tag(title: 'django').save(flush: true)

                // CATEGORIES
                def redhatCategory = new Category(title: 'Red hat', description: 'Projects that concerns red hat products').save(flush: true)
                def jbossCategory = new Category(title: 'JBoss', description: 'Community projects').save(flush: true)
                def rhelCategory = new Category(title: 'RHEL', description: 'Red hat enterprise linux projects').save(flush: true)

                //TOPICS
                def tmsTopic = new Topic(
                        title: 'Thesis management system',
                        owner: owner1,
                        tags: [grailsTag],
                        categories: [
                            redhatCategory,
                            jbossCategory
                        ],
                        types: [Type.BACHELOR],
                        universities: [first],
                        lead: '''Application for managing theses for Red Hat''',
                        description:"Testing Topic"
                ).save(flush: true)
                new Supervision(topic: tmsTopic, supervisor: supervisor1, university: first).save(flush: true)

                def testTopic = new Topic(
                        title: 'Test everything',
                        owner: owner1,
                        tags: [djangoTag],
                        categories: [rhelCategory, jbossCategory],
                        types: [Type.DIPLOMA],
                        universities: [second],
                        lead: '''Test everything twice''',
                        description:"I think you understand"
                ).save(flush: true)
                new Supervision(topic: testTopic, supervisor: supervisor1, university: second).save(flush: true)

                def waitressTopic = new Topic(
                        title: 'Automatic waitress robot',
                        owner: owner1,
                        tags: [djangoTag],
                        categories: [rhelCategory, jbossCategory],
                        types: [Type.DIPLOMA, Type.BACHELOR],
                        universities: [first, second],
                        lead: '''Everyone could use one''',
                        description:"If you can do it, you are hired"
                ).save(flush: true)
                new Supervision(topic: waitressTopic, supervisor: supervisor1, university: second).save(flush: true)
                new Supervision(topic: waitressTopic, supervisor: supervisor2, university: first).save(flush: true)

                //Invalid Topics
                def nouniTopic = new Topic(
                        title: 'Test everything',
                        owner: owner1,
                        tags: [djangoTag],
                        categories: [rhelCategory, jbossCategory],
                        types: [Type.DIPLOMA],
                        lead: '''Test everything twice''',
                        description:"I think you understand"
                ).save(flush: true)
                new Supervision(topic: testTopic, supervisor: supervisor1, university: second).save(flush: true)
                                
                def disabledTopic = new Topic(
                    title: 'Automatic waitress robot',
                    owner: owner1,
                    tags: [djangoTag],
                    categories: [rhelCategory, jbossCategory],
                    types: [Type.DIPLOMA, Type.BACHELOR],
                    universities: [first, second],
                    enabled: false,
                    lead: '''Everyone could use one''',
                        description:"If you can do it, you are hired"
                ).save(flush: true)
                new Supervision(topic: waitressTopic, supervisor: supervisor1, university: second).save(flush: true)
                new Supervision(topic: waitressTopic, supervisor: supervisor2, university: first).save(flush: true)
                
                //THESES
                def example1Thesis = new Thesis(
                        title: "Implementation of Thesis management system",
                        description: '''Create Topic, file upload for theses so that students can upload their work after
they are done with their thesis and application management.''',
                        assignee: supervisor2,
                        topic: tmsTopic,
                        supervisor: supervisor1,
                        tags: [grailsTag],
                        status: Status.IN_PROGRESS,
                        university: first,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)

                def example2Thesis = new Thesis(
                        title: "Implementation of Test",
                        description: "I did it",
                        assignee: person,
                        topic: tmsTopic,
                        supervisor: supervisor1,
                        tags: [grailsTag],
                        status: Status.FINISHED,
                        university: first,
                        type: Type.BACHELOR
                ).save(failOnError: true, flush: true)

                //APPLICATIONS
                new Application(applicant: person, topic: waitressTopic,
                    university: first, note: 'I want to do it', type: Type.DIPLOMA, status: AppStatus.PENDING).save(flush: true)
                new Application(applicant: example1, topic: tmsTopic,
                    university: first, note: 'I want to do it', type: Type.BACHELOR, status: AppStatus.APPROVED).save(flush: true)
                new Application(applicant: example2, topic: testTopic,
                    university: second, note: 'I want to do it', type: Type.DIPLOMA, status: AppStatus.DECLINED).save(flush: true)
                new Application(applicant: example2, topic: waitressTopic,
                    university: second, note: 'I want to do it', type: Type.DIPLOMA, status: AppStatus.PENDING).save(flush: true)
                new Application(applicant: example1, topic: testTopic,
                    university: second, note: 'I want to do it', type: Type.BACHELOR, status: AppStatus.PENDING).save(flush: true)
                
                //Invalid Applications
                new Application(applicant: example2, topic: tmsTopic,
                    university: first, note: 'I want to do it', type: Type.BACHELOR, status: AppStatus.PENDING).save(flush: true)
            }
        }
    }
}
