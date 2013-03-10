package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class TagService {

    Map<Tag, Long> findAllWithCountUsage(Map params) {
        def result = Tag.executeQuery(
                'select tg, count(tg) from Topic t join t.tags tg group by tg order by count(tg) desc', params)
        def mapResult = [:]
        result.each { mapResult[it[0]] = it[1]}
        mapResult
    }
}
