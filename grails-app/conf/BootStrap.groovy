import com.redhat.theses.University
import com.redhat.theses.auth.User
import com.redhat.theses.University
import com.redhat.theses.Membership
import com.redhat.theses.Tag
import com.redhat.theses.Company
import com.redhat.theses.auth.Role
import com.redhat.theses.auth.UserRole
import com.redhat.theses.Topic
import com.redhat.theses.Comment

class BootStrap {

    def init = { servletContext ->
        List<Role> roles = [
            new Role(authority: 'ADMIN').save(),
            new Role(authority: 'OWNER').save(),
            new Role(authority: 'SUPERVISOR').save(),
            new Role(authority: 'STUDENT').save()
        ]

        def u = new User(username: 'admin', fullName: 'Admin Admin', password: "admin", enabled: true, email: 'admin@gmail.com').save();
        def o = new University(name: 'Masaryk University').save()
        new Membership(user: u, organization: o).save()
        roles.each { new UserRole(role: it, user: u).save() }

        def u2 = new User(username: 'person', fullName: 'Person Person', password: "person", enabled: true, email: 'person@gmail.com').save();
        def o2 = new University(name: 'VUT').save()
        new Membership(user: u2, organization: o2).save()
        roles.each {
           if (it.authority in ['SUPERVISOR', 'STUDENT']) {
               new UserRole(role: it, user: u2).save()
           }
        }

        def c = new Company(name: 'Red Hat').save()

        def t = new Tag(title: 'Root tag').save()
        def t2 = new Tag(title: 'Sub tag 1', parent: t).save()
        def t3 = new Tag(title: 'Sub tag 2', parent: t).save()
        def t4 = new Tag(title: 'Sub sub tag 1', parent: t2).save()

        def topic = new Topic(title: 'Topic 1', owner: u, tags: [t], description: '###Description 1', lead: 'Lead 1', company: c).save()
        new Topic(title: 'Topic 2', owner: u, tags: [t], description: '###Description 2', lead: 'Lead 2', company: c).save()
        new Topic(title: 'Topic 3', owner: u, tags: [t], description: '###Description 3', lead: 'Lead 3', company: c).save()
        new Topic(title: 'Topic 4', owner: u, tags: [t], description: '###Description 4', lead: 'Lead 4', company: c).save()
        new Topic(title: 'Topic 5', owner: u, tags: [t], description: '###Description 5', lead: 'Lead 5', company: c).save()

        new Comment(content: 'Comment 1', user: u, article: topic).save()
        new Comment(content: 'Comment 2', user: u, article: topic).save()
        new Comment(content: 'Comment 3', user: u, article: topic).save()
        new Comment(content: 'Comment 4', user: u, article: topic).save()
        new Comment(content: 'Comment 5', user: u, article: topic).save()
    }
    def destroy = {
    }
}
