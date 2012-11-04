package com.redhat.theses.ajax

import com.redhat.theses.Membership
import grails.converters.JSON
import com.redhat.theses.auth.User

class JsonController {
    def listUsersFromUniversityByName(String term, Long organizationId) {
        def query = Membership.where {organization.id == organizationId  && user.fullName =~ "%${term}%"}
        def memberships = query.find([max: 5])
        def userList = []

        memberships.each {
            userList << [id: it.id, label: it.user.fullName, name: it.user.fullName]
        }
        render userList as JSON
    }

    def listUsersByName(String term) {
        def users = User.findAllByFullNameLike("%${term}%", [max: 5])
        def userList = []
        users.each {
            userList << [id: it.id, label: it.fullName, name: it.fullName]
        }
        render userList as JSON
    }


}
