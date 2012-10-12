package com.redhat.theses



import org.junit.*
import grails.test.mixin.*

@TestFor(TopicController)
@Mock(Topic)
class TopicControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/topic/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.topicInstanceList.size() == 0
        assert model.topicInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.topicInstance != null
    }

    void testSave() {
        controller.save()

        assert model.topicInstance != null
        assert view == '/topic/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/topic/show/1'
        assert controller.flash.message != null
        assert Topic.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/topic/list'

        populateValidParams(params)
        def topic = new Topic(params)

        assert topic.save() != null

        params.id = topic.id

        def model = controller.show()

        assert model.topicInstance == topic
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/topic/list'

        populateValidParams(params)
        def topic = new Topic(params)

        assert topic.save() != null

        params.id = topic.id

        def model = controller.edit()

        assert model.topicInstance == topic
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/topic/list'

        response.reset()

        populateValidParams(params)
        def topic = new Topic(params)

        assert topic.save() != null

        // test invalid parameters in update
        params.id = topic.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/topic/edit"
        assert model.topicInstance != null

        topic.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/topic/show/$topic.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        topic.clearErrors()

        populateValidParams(params)
        params.id = topic.id
        params.version = -1
        controller.update()

        assert view == "/topic/edit"
        assert model.topicInstance != null
        assert model.topicInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/topic/list'

        response.reset()

        populateValidParams(params)
        def topic = new Topic(params)

        assert topic.save() != null
        assert Topic.count() == 1

        params.id = topic.id

        controller.delete()

        assert Topic.count() == 0
        assert Topic.get(topic.id) == null
        assert response.redirectedUrl == '/topic/list'
    }
}
