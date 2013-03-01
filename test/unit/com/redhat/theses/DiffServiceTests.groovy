package com.redhat.theses



import grails.test.mixin.*
import org.junit.Before

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(DiffService)
class DiffServiceTests {

    class TestClass {
        def prop1, prop2, prop3
    }

    def object1
    def object2

    @Before
    void setUp() {
        object1 = new TestClass()
        object1.prop1 = "ahoj"
        object1.prop2 = "vole"
        object2 = new TestClass()
        object2.prop1 = "hoj"
        object2.prop2 = "vole"
        object2.prop3 = "sth"
    }

    void testDiff() {
        assert service.diff(object1, object2) == [prop1:['ahoj', 'hoj'], prop3:[null, 'sth']]
    }

    void testDiffArgumentsTypesDifferes() {
        shouldFail(IllegalArgumentException) {
            service.diff(new String(), new Object())
        }
    }

    void testCreateHtmlTable() {
        def diff = service.diff(object1, object2)
        assert service.createHtmlTable(diff) ==
                '<table>' +
                    '<tr><th></th><th>Old value</th><th>New value</th></tr>' +
                    '<tr><td>prop3</td><td>null</td><td>sth</td></tr>' +
                    '<tr><td>prop1</td><td>ahoj</td><td>hoj</td></tr>' +
                '</table>'
    }
}