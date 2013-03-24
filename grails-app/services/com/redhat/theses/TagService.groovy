package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class TagService {
    // TODO: following two method probably could be merged into one with Class parameter

    Map<Tag, Long> findAlTopicslWithCountUsage(Map params) {
        def result = Tag.executeQuery(
                'select tg, count(tg) from Topic t join t.tags tg group by tg order by count(tg) desc, tg.title asc', params)
        def mapResult = [:]
        result.each { mapResult[it[0]] = it[1]}
        mapResult
    }

    Map<Tag, Long> findAlTheseslWithCountUsage(Map params) {
        def result = Tag.executeQuery(
                'select tg, count(tg) from Thesis t join t.tags tg group by tg order by count(tg) desc', params)
        def mapResult = [:]
        result.each { mapResult[it[0]] = it[1]}
        mapResult
    }
}
