package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class TagService {

    /**
     * Finds all tags with the number of usages in a map
     *
     * @param articleClass - Class subclass of Article
     * @param params - pagination params
     * @param sort - sort and order in string form
     * @return - Map containing tag as a key and number of usages as a value
     */
    Map<Tag, Long> findAllWithCountUsage(Class articleClass, Map params, String sort = "count(tag) desc, tag.title asc") {
        def result = Tag.executeQuery(
                "select tag, count(tag) from ${articleClass.simpleName} t join t.tags tag " +
                        "group by tag order by ${sort}", params)
        def mapResult = [:]
        result.each { mapResult[it[0]] = it[1] }
        mapResult
    }
}
