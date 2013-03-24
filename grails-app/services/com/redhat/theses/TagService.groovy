package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class TagService {

    Map<Tag, Long> findAllWithCountUsage(Class articleClass, Map params) {
        def result = Tag.executeQuery(
                "select tg, count(tg) from ${articleClass.simpleName} t join t.tags tg " +
                        "group by tg order by count(tg) desc, tg.title asc", params)
        def mapResult = [:]
        result.each { mapResult[it[0]] = it[1]}
        mapResult
    }
}
