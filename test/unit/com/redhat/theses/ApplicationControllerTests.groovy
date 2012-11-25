package com.redhat.theses



import org.junit.*
import grails.test.mixin.*

@TestFor(ApplicationController)
@Mock(Application)
class ApplicationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/application/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.applicationInstanceList.size() == 0
        assert model.applicationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.applicationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.applicationInstance != null
        assert view == '/application/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/application/show/1'
        assert controller.flash.message != null
        assert Application.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/application/list'

        populateValidParams(params)
        def application = new Application(params)

        assert application.save() != null

        params.id = application.id

        def model = controller.show()

        assert model.applicationInstance == application
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/application/list'

        populateValidParams(params)
        def application = new Application(params)

        assert application.save() != null

        params.id = application.id

        def model = controller.edit()

        assert model.applicationInstance == application
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/application/list'

        response.reset()

        populateValidParams(params)
        def application = new Application(params)

        assert application.save() != null

        // test invalid parameters in update
        params.id = application.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/application/edit"
        assert model.applicationInstance != null

        application.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/application/show/$application.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        application.clearErrors()

        populateValidParams(params)
        params.id = application.id
        params.version = -1
        controller.update()

        assert view == "/application/edit"
        assert model.applicationInstance != null
        assert model.applicationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/application/list'

        response.reset()

        populateValidParams(params)
        def application = new Application(params)

        assert application.save() != null
        assert Application.count() == 1

        params.id = application.id

        controller.delete()

        assert Application.count() == 0
        assert Application.get(application.id) == null
        assert response.redirectedUrl == '/application/list'
    }
}
