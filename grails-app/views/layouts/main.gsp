<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.min.css')}" type="text/css">
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-ui.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'a4g.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'richg.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'base.css')}" type="text/css">
    <g:layoutHead/>
    <r:layoutResources />
</head>
<body>

    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <div class="span12">
                    <a class="brand" href="#">Theses Management System</a>
                    <form class="navbar-search pull-right">
                        <input type="text" class="search-query" placeholder="Search">
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="span12">
            <ul class="nav nav-pills" style="padding-top: 70px;">
                <li id="news-feed" class="${pageProperty(name:'body.nav-button') == 'news-feed' ? 'active' : ''}">
                    <a href="/theses-management">News Feed</a>
                </li>
                <li id="topics" class="${pageProperty(name:'body.nav-button') == 'topics' ? 'active' : ''}">
                    <g:link controller="topic">Topics</g:link>
                </li>
                <li id="theses">
                    <a href="#">Theses</a>
                </li>
                <li id="projects">
                    <a href="#">Projects</a>
                </li>
            </ul>
            
            <g:if test="${flash.message}">
                <div class="alert alert-info" role="status">
                    ${flash.message}
                </div>
            </g:if>
        </div>

        <g:layoutBody />
    </div>

    <footer class="footer">
        <div class="container">
            <div class="span12">
                <p class="pull-right"><a href="#">Back to top</a></p>
            </div>
        </div>
    </footer>

    <g:javascript library="application"/>
    <r:layoutResources />

</body>
</html>
