class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
        "400"(view:'/errors/400')
        "403"(view:'/errors/403')
        "404"(view:'/errors/404')
        "500"(view:'/errors/500')
	}
}
