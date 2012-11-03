package com.redhat.theses

class Tag {

    static hasMany = [subTags: Tag]
    static belongsTo = [parent: Tag]
    static transients = ['allParents', 'allSubtags']

    String title

    static constraints = {
        title   unique: true
        parent  nullable: true
    }

    // TODO: some possible refactoring
    List<Tag> getAllParents(){
        def result = []
        for (def p = parent; p != null; p = p.parent){
            result << p
        }
        result
    }

    List<Tag> getAllSubTags(){
        subTags ? subTags*.allSubTags.flatten() + subTags : []
    }

    String toString(){
        title
    }
}