class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'home', action: 'index')
        "400"(view:'/errors/400')
        "403"(view:'/errors/403')
        "404"(view:'/errors/404')
        "500"(view:'/errors/500')
	}
}
