package com.redhat.theses

import grails.test.mixin.*

@TestFor(OrganizationController)
@Mock(Organization)
class OrganizationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/organization/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.organizationInstanceList.size() == 0
        assert model.organizationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.organizationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.organizationInstance != null
        assert view == '/organization/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/organization/show/1'
        assert controller.flash.message != null
        assert Organization.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/organization/list'

        populateValidParams(params)
        def organization = new Organization(params)

        assert organization.save() != null

        params.id = organization.id

        def model = controller.show()

        assert model.organizationInstance == organization
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/organization/list'

        populateValidParams(params)
        def organization = new Organization(params)

        assert organization.save() != null

        params.id = organization.id

        def model = controller.edit()

        assert model.organizationInstance == organization
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/organization/list'

        response.reset()

        populateValidParams(params)
        def organization = new Organization(params)

        assert organization.save() != null

        // test invalid parameters in update
        params.id = organization.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/organization/edit"
        assert model.organizationInstance != null

        organization.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/organization/show/$organization.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        organization.clearErrors()

        populateValidParams(params)
        params.id = organization.id
        params.version = -1
        controller.update()

        assert view == "/organization/edit"
        assert model.organizationInstance != null
        assert model.organizationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/organization/list'

        response.reset()

        populateValidParams(params)
        def organization = new Organization(params)

        assert organization.save() != null
        assert Organization.count() == 1

        params.id = organization.id

        controller.delete()

        assert Organization.count() == 0
        assert Organization.get(organization.id) == null
        assert response.redirectedUrl == '/organization/list'
    }
}
