modules = {

    'application' {
        dependsOn 'jquery'
        resource url: [dir: 'js', file: 'application.js']
    }
    'a4g' {
        dependsOn 'jquery, bootstrap-js'
        resource url: [dir: 'js', file: 'a4g.js'], disposition: 'head'
        resource url: [dir: 'js', file: 'a4g-bottom.js']
    }
    'richg' {
        dependsOn 'jquery'
        resource url: [dir: 'js', file: 'richg.js'], disposition: 'head'
    }
    'bootstrap-js' {
        resource url: [dir: 'js', file: 'bootstrap.min.js']
    }
    'bootstrap-less' {
        dependsOn 'bootstrap-js'
        resource url: [dir: 'less', file: 'bootstrap.less'], attrs: [rel: 'stylesheet/less', type: 'css']
    }

}
