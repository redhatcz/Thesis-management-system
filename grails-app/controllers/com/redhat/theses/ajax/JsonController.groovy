package com.redhat.theses.ajax

import com.redhat.theses.Membership
import com.redhat.theses.Topic
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
 * Following methods provide data for autocomplete
 */

    static final Integer MAX_RESULTS = 5

    def listUsersFromUniversityByName(String term, Long organizationId) {
        def query = Membership.where {organization.id == organizationId  && user.fullName =~ "%${term}%"}
        def memberships = query.list([max: MAX_RESULTS])
        def userMap = [:]

        memberships.each {
            userMap[it.user.fullName] = it.id
        }
        render userMap as JSON
    }

    def listUsersByName(String term) {
        def users = User.findAllByFullNameIlike("%${term}%", [max: MAX_RESULTS])
        def userMap = [:]
        users.each {
            userMap[it.fullName] = it.id
        }
        render userMap as JSON
    }

    def listTagsByName(String term){
        def tags = Tag.findAllByTitleIlike("%${term}%", [max: MAX_RESULTS])
        def tagMap = [:]
        tags.each {
            tagMap[it.title] = it.id
        }
        render tagMap as JSON
    }

    def listTopicsByTitle(String term) {
        def topics = Topic.findAllByTitleIlike("%${term}%", [max: MAX_RESULTS])
        def topicMap = [:]
        topics.each {
            topicMap[it.title] = it.id
        }
        render topicMap as JSON
    }

/*
 * Following methods provide data for dynamic selects
 */

    def listSupervisorsFromUniversity(Long topicId,  Long organizationId) {
        def users = User.executeQuery('''SELECT s.membership.user
                FROM  Supervision s
                WHERE s.topic.id = :topicId AND s.membership.organization.id = :organizationId''',
                [topicId: topicId, organizationId: organizationId])
        def userMap = [:]

        users.each {
            userMap[it.fullName] = it.id
        }
        render userMap as JSON
    }

    def listSupervisorsForUser(Long topicId,  Long userId) {
        def userMap = [:]
        if (topicId && userId) {
            def users = User.executeQuery('''SELECT s.membership.user
                    FROM  Supervision s
                    WHERE s.topic.id = :topicId AND s.membership.user.id = :userId''',
                    [topicId: topicId, userId: userId])

            users.each {
                userMap[it.fullName] = it.id
            }
        }
        render userMap as JSON
    }
}
