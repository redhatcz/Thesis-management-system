<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <r:require module="bootstrap-js"/>
    <r:require module="bootstrap-less"/>
    <r:require module="a4g"/>
    <r:require module="richg"/>
    <r:require module="application"/>
    <g:layoutHead/>
    <r:layoutResources />
</head>
<body>
    <div class="navbar navbar-fixed-top">
        <div class="navbar-tms">
            <div class="container">
                <div class="head">
                    <g:link uri="/">
                        Theses Management System
                    </g:link>
                    <sec:ifLoggedIn>
                    <div class="pull-right">
                        <g:link controller="profile">
                            <sec:loggedInUserInfo field="fullName"/>
                        </g:link>
                        <div class="dropdown" style="display: inline;">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <span class="tms-settings medium-big"></span>
                            </a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                <li><g:link tabindex="-1">Edit Profile</g:link></li>
                                <li class="divider"></li>
                                <li><g:link tabindex="-1" controller="logout">Sign Out</g:link></li>
                            </ul>
                        </div>
                    </div>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                    <div class="pull-right">
                        <g:link controller="login" action="auth">Sign In</g:link>
                    </div>
                    </sec:ifNotLoggedIn>
                </div>
            </div>
        </div>
    </div>
    <div class="margin"></div>

    <div class="container">
        <g:pageProperty name="page.container-box"/>
    </div>

    <footer class="footer">
        <div class="container">
            <div class="span12">
                <p class="pull-right">CopyRight &copy; 2013 Red Hat Inc.</p>
            </div>
        </div>
    </footer>
    <r:layoutResources />
</body>
</html>
