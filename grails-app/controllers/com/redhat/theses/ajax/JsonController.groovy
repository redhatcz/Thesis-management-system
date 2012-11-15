package com.redhat.theses.ajax

import com.redhat.theses.Membership
import grails.converters.JSON
import com.redhat.theses.auth.User

import com.redhat.theses.Tag
import com.redhat.theses.Comment
/**
 * TODO: if the key of the map that is returned as JSON is not unique,
 * we should return something that is unique, e.g. "Full Name (id)"
 */
class JsonController {
    static allowedMethods = [createComment: 'POST']

    def springSecurityService

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

    def createComment() {
        Comment comment = new Comment(params.comment)
        comment.setUser((User) springSecurityService.currentUser)

        if (!comment.save(flush: true)) {
            def message = richg.alert type: 'error', message: 'An error has occured and the comment could not be saved.'
            render([success: false, message: message] as JSON)
            return
        }

        def data = richg.comment comment: comment, index: params.comment.index
        def message = richg.alert type: 'info', message: 'Your comment has been successfully created.'

        render([success: true, data: data, message: message] as JSON)
    }
}
