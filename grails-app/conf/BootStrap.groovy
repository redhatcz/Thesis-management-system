import com.redhat.theses.University
import com.redhat.theses.auth.User
import com.redhat.theses.University
import com.redhat.theses.Membership
import com.redhat.theses.Tag
import com.redhat.theses.Company

class BootStrap {

    def init = { servletContext ->
        if(!User.count() ){
            def u = new User(username: 'admin', fullName: 'Admin Admin', password: "admin", enabled: true).save();
            def o = new University(name: 'Masaryk University').save()
            new Membership(user: u, organization: o).save()

            def u2 = new User(username: 'person', fullName: 'Person Person', password: "person", enabled: true).save();
            def o2 = new University(name: 'VUT').save()
            new Membership(user: u2, organization: o2).save()

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
