import org.grails.plugin.resource.mapper.MapperPhase

class WoffResourceMapper {

    static phase = MapperPhase.ALTERNATEREPRESENTATION
    static defaultIncludes = [ '**/*.woff' ]

    def map(resource, config) {
        resource.requestProcessors << { req, resp ->
            resp.setHeader('Content-Type', 'font/opentype')
        }
    }

}