package com.redhat.theses



import org.junit.*
import grails.test.mixin.*

@TestFor(UniversityController)
@Mock(University)
class UniversityControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/university/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.universityInstanceList.size() == 0
        assert model.universityInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.universityInstance != null
    }

    void testSave() {
        controller.save()

        assert model.universityInstance != null
        assert view == '/university/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/university/show/1'
        assert controller.flash.message != null
        assert University.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/university/list'

        populateValidParams(params)
        def university = new University(params)

        assert university.save() != null

        params.id = university.id

        def model = controller.show()

        assert model.universityInstance == university
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/university/list'

        populateValidParams(params)
        def university = new University(params)

        assert university.save() != null

        params.id = university.id

        def model = controller.edit()

        assert model.universityInstance == university
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/university/list'

        response.reset()

        populateValidParams(params)
        def university = new University(params)

        assert university.save() != null

        // test invalid parameters in update
        params.id = university.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/university/edit"
        assert model.universityInstance != null

        university.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/university/show/$university.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        university.clearErrors()

        populateValidParams(params)
        params.id = university.id
        params.version = -1
        controller.update()

        assert view == "/university/edit"
        assert model.universityInstance != null
        assert model.universityInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/university/list'

        response.reset()

        populateValidParams(params)
        def university = new University(params)

        assert university.save() != null
        assert University.count() == 1

        params.id = university.id

        controller.delete()

        assert University.count() == 0
        assert University.get(university.id) == null
        assert response.redirectedUrl == '/university/list'
    }
}
