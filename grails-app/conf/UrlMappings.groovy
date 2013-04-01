class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?/$headline?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'home', action: 'index')
        "400"(controller: 'errors', action: '400')
        "403"(controller: 'errors', action: '403')
        "404"(controller: 'errors', action: '404')
        "500"(controller: 'errors', action: '500')

        //exclude searchable controller
        "/searchable"(controller: 'errors', action: '404')
        "/searchable/$action?"(controller: 'errors', action: '404')
	}
}
