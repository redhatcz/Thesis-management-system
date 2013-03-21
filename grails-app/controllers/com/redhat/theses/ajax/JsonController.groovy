package com.redhat.theses.ajax
import com.redhat.theses.Category
import com.redhat.theses.Tag
import com.redhat.theses.Topic
import com.redhat.theses.auth.User
import grails.converters.JSON
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

    def listUsersByNameAndRole(String term, String role) {
        def supervisors = User.executeQuery(
                "from User where :role in elements(roles) and lower(fullName) like :fullName",
                [role: role, fullName: "%${term?.toLowerCase()}%"],
                [max: MAX_RESULTS])
        def userMap = [:]

        supervisors.each {
            userMap[it.id] = it.fullName
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

    def listCategoriesByName(String term){
        def categories = Category.findAllByTitleIlike("%${term}%", [max: MAX_RESULTS])
        def categoryMap = [:]
        categories.each {
            categoryMap[it.id] = it.title
        }
        render categoryMap as JSON
    }

    def listTagsByTitle(String term){
        render Tag.findAllByTitleIlike("%${term}%", [max: MAX_RESULTS]).collect {it.title} as JSON
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

    def listSupervisorsFromUniversity(Long topicId,  Long universityId) {
        def userMap = [:]

        if (topicId && universityId) {
            def users = User.executeQuery('''SELECT s.supervisor
                FROM  Supervision s
                WHERE s.topic.id = :topicId AND s.university.id = :universityId''',
                    [topicId: topicId, universityId: universityId])

            users.each {
                userMap[it.id] = it.fullName
            }
        }

        render userMap as JSON
    }
}
