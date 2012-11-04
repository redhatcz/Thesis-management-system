package com.redhat.theses.ajax

import com.redhat.theses.Membership
import grails.converters.JSON
import com.redhat.theses.auth.User
import javax.servlet.jsp.tagext.TagSupport
import com.redhat.theses.Tag

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

    def listTagsByName(String term){
        def tags = Tag.findAllByTitleLike("%${term}%", [max: 5])
        def tagList = []
        tags.each {
            tagList << [id: it.id, label: it.title, title: it.title]
        }
        render tagList as JSON
    }


}
