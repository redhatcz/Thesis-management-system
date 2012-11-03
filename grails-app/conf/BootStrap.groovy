import com.redhat.theses.University
import com.redhat.theses.auth.User
import com.redhat.theses.University
import com.redhat.theses.Membership
import com.redhat.theses.Tag
import com.redhat.theses.Company
import com.redhat.theses.auth.Role
import com.redhat.theses.auth.UserRole

class BootStrap {

    def init = { servletContext ->
        List<Role> roles
        if(!Role.count()) {
            roles = [
                new Role(authority: 'ADMIN').save(),
                new Role(authority: 'OWNER').save(),
                new Role(authority: 'SUPERVISOR').save(),
                new Role(authority: 'STUDENT').save()
            ]
        } else {
            roles = Role.findAll()
        }
        if(!User.count() ){
            def u = new User(username: 'admin', fullName: 'Admin Admin', password: "admin", enabled: true).save();
            def o = new University(name: 'Masaryk University').save()
            new Membership(user: u, organization: o).save()
            roles.each { new UserRole(role: it, user: u).save() }

            def u2 = new User(username: 'person', fullName: 'Person Person', password: "person", enabled: true).save();
            def o2 = new University(name: 'VUT').save()
            new Membership(user: u2, organization: o2).save()
            roles.each {
               if (it.authority in ['SUPERVISOR', 'STUDENT']) {
                   new UserRole(role: it, user: u2).save()
               }
            }

            def c = new Company(name: 'Red Hat').save()
        }
        if(!Tag.count()){
            def t = new Tag(title: 'Root tag').save()
            def t2 = new Tag(title: 'Sub tag 1', parent: t).save()
            def t3 = new Tag(title: 'Sub tag 2', parent: t).save()
            def t4 = new Tag(title: 'Sub sub tag 1', parent: t2).save()
        }
    }
    def destroy = {
    }
}
