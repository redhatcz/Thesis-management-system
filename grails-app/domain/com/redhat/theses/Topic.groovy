 package com.redhat.theses

import com.redhat.theses.auth.User

class Topic extends Article {

    String secondaryTitle

    String lead
    String description
    String secondaryDescription
    Date dateCreated
    User owner
    Boolean enabled = true

    static hasMany = [universities: University, categories: Category, tags: Tag, types: Type]

    static hibernateFilters = {
        enabledFilter(condition:"enabled='1'", default: true)
    }

    static constraints = {
        description widget: 'textarea' , nullable: false, blank: false
        lead nullable: false, blank: false
        secondaryTitle nullable: true
        secondaryDescription nullable: true

        owner validator: {owner ->
            if (owner?.id == null || !User.get(owner.id)) {
                'not.found'
            }
        }
    }

    static mapping = {
        description type: 'text'
        lead type: 'text'
        secondaryDescription type: 'text'
        sort 'dateCreated'
        order 'desc'
    }

    static List<Topic> findAllByUniversity(University university, Map params = [:]) {
        Topic.findAll(
                'from Topic t where :university member of t.universities order by t.dateCreated desc',
                [university: university], params)
    }

    static List<Topic> findAllByCategory(Category category, Map params = [:]){
        Topic.findAll(
                'from Topic t where :category member of t.categories order by t.dateCreated desc',
                [category: category], params)
    }

    static Long countByCategory(Category category){
        Topic.executeQuery('select count(distinct t) from Topic t where :category member of t.categories',
                [category: category])[0]
    }

    static List<Topic> findAllByTag(Tag tag, Map params = [:]){
        Topic.findAll('from Topic t where :tag member of t.tags order by t.dateCreated desc', [tag: tag], params)
    }

    static Long countByTag(Tag tag){
        Topic.executeQuery('select count(distinct t) from Topic t where :tag member of t.tags', [tag: tag])[0]
    }

    static List<Topic> findAllByCategoryAndTag(Category category, Tag tag, Map params = [:]){
        Topic.findAll(
                'from Topic t where :tag member of t.tags and :category member of t.categories order by t.dateCreated desc',
                [tag: tag, category: category], params)
    }

    static Long countByCategoryAndTag(Category category, Tag tag){
        Topic.executeQuery('select count(distinct t) from Topic t ' +
                'where :tag member of t.tags and :category member of t.categories',
                [tag: tag, category: category])[0]
    }

    static List<Topic> findAllBySupervisor(User supervisor, Map params = [:]){
        Topic.executeQuery('SELECT s.topic FROM Supervision s WHERE s.supervisor=:supervisor',
                [supervisor: supervisor], params).unique()
    }

    List<Supervision> getSupervisions() {
        Supervision.findAllByTopic(this)
    }

    List<User> getSupervisors() {
        Supervision.findAllByTopic(this)*.supervisor.unique()
    }

    String toString(){
        title
    }
}
