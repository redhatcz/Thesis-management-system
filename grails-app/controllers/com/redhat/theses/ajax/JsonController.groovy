package com.redhat.theses.ajax

import com.redhat.theses.Membership
import grails.converters.JSON
import com.redhat.theses.auth.User

import com.redhat.theses.Tag
import com.redhat.theses.Comment
import com.redhat.theses.Supervision
/**
 * TODO: if the key of the map that is returned as JSON is not unique,
 * we should return something that is unique, e.g. "Full Name (id)"
 */
class JsonController {

/*
    These methods are providing data for autocomplete
     */
    def listUsersFromUniversityByName(String term, Long organizationId) {
        def query = Membership.where {organization.id == organizationId  && user.fullName =~ "%${term}%"}
        def memberships = query.list([max: 5])
        def userMap = [:]

        memberships.each {
            userMap[it.user.fullName] = it.id
        }
        render userMap as JSON
    }

    def listUsersByName(String term) {
        def users = User.findAllByFullNameIlike("%${term}%", [max: 5])
        def userMap = [:]
        users.each {
            userMap[it.fullName] = it.id
        }
        render userMap as JSON
    }

    def listTagsByName(String term){
        def tags = Tag.findAllByTitleIlike("%${term}%", [max: 5])
        def tagMap = [:]
        tags.each {
            tagMap[it.title] = it.id
        }
        render tagMap as JSON
    }

/*
    These methods are providing data for dynamic selects
 */
    def listSupervisorsFromUniversity(Long topicId,  Long organizationId) {
        def users = User.executeQuery('''SELECT s.membership.user
                FROM  Supervision s
                WHERE s.topic.id = :topidId AND s.membership.organization.id = :organizationId''',
                [topidId: topicId, organizationId: organizationId])
        def userMap = [:]

        users.each {
            userMap[it.fullName] = it.id
        }
        render userMap as JSON
    }
}
