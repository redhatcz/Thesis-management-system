package com.redhat.theses.ajax

import com.redhat.theses.Membership
import com.redhat.theses.Supervision
import com.redhat.theses.SupervisionService
import com.redhat.theses.Topic
import grails.converters.JSON
import com.redhat.theses.auth.User

import com.redhat.theses.Tag
/**
 * TODO: if the key of the map that is returned as JSON is not unique,
 * we should return something that is unique, e.g. "Full Name (id)"
 */
class JsonController {

    def springSecurityService

/*
 * Following methods provide data for autocomplete
 */

    static final Integer MAX_RESULTS = 5

    def listUsersFromUniversityByName(String term, Long organizationId) {
        def query = Membership.where {organization.id == organizationId  && user.fullName =~ "%${term}%"}
        def memberships = query.list([max: MAX_RESULTS])
        def userMap = [:]

        memberships.each {
            userMap[it.id] = it.user.fullName
        }
        render userMap as JSON
    }

    def listUsersByName(String term) {
        def users = User.findAllByFullNameIlike("%${term}%", [max: MAX_RESULTS])
        def userMap = [:]
        users.each {
            userMap[it.id] = it.fullName
        }
        render userMap as JSON
    }

    def listTagsByName(String term){
        def tags = Tag.findAllByTitleIlike("%${term}%", [max: MAX_RESULTS])
        def tagMap = [:]
        tags.each {
            tagMap[it.id] = it.title
        }
        render tagMap as JSON
    }

    def listTopicsByTitle(String term) {
        def topics = Topic.findAllByTitleIlike("%${term}%", [max: MAX_RESULTS])
        def topicMap = [:]
        topics.each {
            topicMap[it.id] = it.title
        }
        render topicMap as JSON
    }

/*
 * Following methods provide data for dynamic selects
 */

    def listSupervisorsFromUniversity(Long topicId,  Long organizationId) {
        def userMap = [:]

        if (topicId && organizationId) {
            def users = User.executeQuery('''SELECT s.membership.user
                FROM  Supervision s
                WHERE s.topic.id = :topicId AND s.membership.organization.id = :organizationId''',
                    [topicId: topicId, organizationId: organizationId])

            users.each {
                userMap[it.id] = it.fullName
            }
        }

        render userMap as JSON
    }

    def listMembershipsWithinOrganization(Long topicId, Long organizationId) {
        def membershipMap = [:]

        if (topicId && organizationId) {
            def memberships = Supervision.executeQuery('''SELECT s.membership
                    FROM  Supervision  s
                    WHERE s.topic.id = :topicId AND  s.membership.organization.id = :organizationId''',
                    [organizationId: organizationId, topicId: topicId])

            memberships.each {
                membershipMap[it.id] = it.user.fullName
            }
        }


        render membershipMap as JSON
    }

    def listUniversitiesForUserWithinTopic(Long topicId, Long userId){
        def universityMap = [:]

        if (topicId && userId) {
            def topicInstance = Topic.get(topicId)
            User user = User.get(userId);

            def topicUniversities = Supervision.executeQuery('''SELECT  s.membership.organization
                    FROM Supervision  s
                    WHERE  s.topic.id = :topicId''',
                    [topicId: topicId])

            def universities = user.organizations.findAll {it.id in topicUniversities*.id}

            universities.each {
                universityMap[it.id] = it.name
            }
        }

        render universityMap as JSON
    }

    def listSupervisorsForUser(Long topicId,  Long userId) {
        def userMap = [:]
        if (topicId && userId) {
            def users = User.executeQuery('''SELECT s.membership.user
                    FROM  Supervision s
                    WHERE s.topic.id = :topicId AND s.membership.user.id = :userId''',
                    [topicId: topicId, userId: userId])

            users.each {
                userMap[it.id] = it.fullName
            }
        }
        render userMap as JSON
    }
}
