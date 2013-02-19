<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <g:render template="/layouts/head"/>
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
                                <span class="entypo-settings medium-big"></span>
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
    <div class="manage">
        <g:pageProperty name="page.manage-box"/>
    </div>

    <div class="container">
        <g:pageProperty name="page.container-box"/>
    </div>

    <footer>
        <div class="footer-up">
            <div class="container">
                <div class="span3">
                    <p class="leading">Developers</p>
                    <a href="#">Get Started</a>
                    <a href="#">User Guide</a>
                    <a href="#">FAQ</a>
                    <a href="#">Pricing</a>
                </div>
                <div class="span3">
                    <p class="leading">Comunity</p>
                    <a href="#">Blog</a>
                    <a href="#">Forum</a>
                    <a href="#">IRC Channel</a>
                    <a href="#">Feedback</a>
                </div>                    
                <div class="span3">
                    <p class="leading">Get Involved</p>
                    <a href="#">Open Source</a>
                    <a href="#">Newsletter Sign Up</a>
                    <a href="#">Make It Better</a>
                    <a href="#">GitHub</a>
                </div>                    
                <div class="span3">
                    <p class="leading">Account</p>
                    <a href="#">Terms of Service</a>
                    <a href="#">Privacy Policy</a>
                    <a href="#">Security Policy</a>
                    <a href="#">Plans</a>
                </div>
            </div>
        </div>
        <div class="footer-down">
            <div class="container">
                <img src="${resource(dir: 'images', file: 'redhat.png')}">
                <p class="pull-right">CopyRight &copy; 2013 Red Hat, Inc.</p>
            </div>
        </div>
    </footer>
    <r:layoutResources />
</body>
</html>
