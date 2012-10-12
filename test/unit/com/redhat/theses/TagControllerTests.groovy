package com.redhat.theses



import org.junit.*
import grails.test.mixin.*

@TestFor(TagController)
@Mock(Tag)
class TagControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/tag/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.tagInstanceList.size() == 0
        assert model.tagInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.tagInstance != null
    }

    void testSave() {
        controller.save()

        assert model.tagInstance != null
        assert view == '/tag/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/tag/show/1'
        assert controller.flash.message != null
        assert Tag.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/tag/list'

        populateValidParams(params)
        def tag = new Tag(params)

        assert tag.save() != null

        params.id = tag.id

        def model = controller.show()

        assert model.tagInstance == tag
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/tag/list'

        populateValidParams(params)
        def tag = new Tag(params)

        assert tag.save() != null

        params.id = tag.id

        def model = controller.edit()

        assert model.tagInstance == tag
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/tag/list'

        response.reset()

        populateValidParams(params)
        def tag = new Tag(params)

        assert tag.save() != null

        // test invalid parameters in update
        params.id = tag.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/tag/edit"
        assert model.tagInstance != null

        tag.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/tag/show/$tag.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        tag.clearErrors()

        populateValidParams(params)
        params.id = tag.id
        params.version = -1
        controller.update()

        assert view == "/tag/edit"
        assert model.tagInstance != null
        assert model.tagInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/tag/list'

        response.reset()

        populateValidParams(params)
        def tag = new Tag(params)

        assert tag.save() != null
        assert Tag.count() == 1

        params.id = tag.id

        controller.delete()

        assert Tag.count() == 0
        assert Tag.get(tag.id) == null
        assert response.redirectedUrl == '/tag/list'
    }
}
