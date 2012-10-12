import com.redhat.theses.University
import com.redhat.theses.auth.User
import com.redhat.theses.University
import com.redhat.theses.Membership

class BootStrap {

    def init = { servletContext ->
        if(!User.count() ){
            def u = new User(username: 'admin', fullName: 'Admin Admin', password: "admin", enabled: true).save();
            def o = new University(name: 'Masaryk University').save()
            new Membership(user: u, university: o).save()
        }
    }
    def destroy = {
    }
}
