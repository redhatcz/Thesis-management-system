import com.redhat.theses.University
import com.redhat.theses.auth.User
import com.redhat.theses.University
import com.redhat.theses.Membership
import com.redhat.theses.Tag

class BootStrap {

    def init = { servletContext ->
        if(!User.count() ){
            def u = new User(username: 'admin', fullName: 'Admin Admin', password: "admin", enabled: true).save();
            def o = new University(name: 'Masaryk University').save()
            new Membership(user: u, university: o).save()

            def u2 = new User(username: 'person', fullName: 'Person Person', password: "person", enabled: true).save();
            def o2 = new University(name: 'VUT').save()
            new Membership(user: u2, university: o2).save()
        }
        if(!Tag.count()){
            def t = new Tag(title: 'Root tag').save()
        }
    }
    def destroy = {
    }
}
