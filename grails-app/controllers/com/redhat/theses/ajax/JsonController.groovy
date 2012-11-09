package com.redhat.theses.ajax

import com.redhat.theses.Membership
import grails.converters.JSON
import com.redhat.theses.auth.User
import javax.servlet.jsp.tagext.TagSupport
import com.redhat.theses.Tag
/**
 * TODO: if the key of the map that is returned as JSON is not unique,
 * we should return something that is unique, e.g. "Full Name (id)"
 */
class JsonController {
    def listUsersFromUniversityByName(String term, Long organizationId) {
        def query = Membership.where {organization.id == organizationId  && user.fullName =~ "%${term}%"}
        def memberships = query.find([max: 5])
        def userMap = [:]

        memberships.each {
            userMap[it.user.fullName] = it.id
        }
        render userMap as JSON
    }

    def listUsersByName(String term) {
        def users = User.findAllByFullNameLike("%${term}%", [max: 5])
        def userMap = [:]
        users.each {
            userMap[it.fullName] = it.id
        }
        render userMap as JSON
    }

    def listTagsByName(String term){
        def tags = Tag.findAllByTitleLike("%${term}%", [max: 5])
        def tagMap = [:]
        tags.each {
            tagMap[it.title] = it.id
        }
        render tagMap as JSON
    }


}
