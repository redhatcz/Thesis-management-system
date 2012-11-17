modules = {

    'custom-bootstrap' {
        resource url: [dir: 'less', file: 'custom-bootstrap.less'], attrs: [rel: 'stylesheet/less', type:'css']
    }
    'application' {
        dependsOn 'jquery'
        resource url: [dir: 'js', file: 'application.js']
    }
    'a4g' {
        dependsOn 'jquery'
        resource url: [dir: 'js', file: 'a4g.js'], disposition: 'head'
    }
    'richg' {
        dependsOn 'jquery'
        resource url: [dir: 'js', file: 'richg.js'], disposition: 'head'
    }

}