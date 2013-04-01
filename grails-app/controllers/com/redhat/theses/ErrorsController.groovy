package com.redhat.theses

/**
 * @author vdedik@redhat.com
 */
class ErrorsController {

    def '400'() {}
    def '403'() {}
    def '404'() {
        //set 404 status for pages that are re-routed here from url mappings
        response.setStatus(404)
    }
    def '500'() {}
}
