package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class TagController {

    static defaultAction = "list"

    /**
     * Dependency injection of com.redhat.theses.TagService
     */
    def tagService

    def list() {
        params.max = 30
        def args = [max: params.max]
        if (params.offset) {
            args << [offset: params.offset]
        }

        def articleClass
        if (params.tagsOf == 'thesis') {
            articleClass = Thesis
        } else {
            articleClass = Topic
        }

        def tagsWithUsage = tagService.findAllWithCountUsage(articleClass, args, "tag.title asc")
        def tags = tagsWithUsage.collect {it.key}
        def tagCount = tagService.countUsedTags(articleClass)

        [tags: tags, tagsWithUsage: tagsWithUsage, tagCount: tagCount]
    }
}
