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
        String where = ""
        if (articleClass == Topic) {
            where = "where t.enabled = true"
        }
        def result = Tag.executeQuery(
                "select tag, count(tag) from ${articleClass.simpleName} t " +
                        "join t.tags tag ${where} " +
                        "group by tag order by ${sort}", params)
        def mapResult = [:]
        result.each { mapResult[it[0]] = it[1] }
        mapResult
    }

    /**
     * Counts number of tags that are used
     *
     * @param articleClass -- Class subclass of Article
     * @return number of tags with at least one usage
     */
    def countUsedTags(Class articleClass) {
        Tag.executeQuery(
                "select count(*) from ${articleClass.simpleName} t join t.tags tag group by tag").size()
    }
}
