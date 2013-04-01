package com.redhat.theses

import org.compass.core.engine.SearchEngineQueryParseException

class SearchController {

    /**
     * Dependency injection of grails.plugin.searchable.SearchableService
     */
    def searchableService

    def index() {
        if (!params.q?.trim()) {
            return [:]
        }

        try {
            def searchResult = searchableService.search(params.q, params)
            [searchResult: searchResult]
        } catch (SearchEngineQueryParseException ex) {
            log.warn("Search parse error: " + ex.message)
            [:]
        }
    }
}
