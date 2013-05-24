<%@ page import="com.redhat.theses.auth.User; com.redhat.theses.Notification" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <g:render template="/layouts/head"/>
</head>
<body>
    <div class="navbar navbar-static-top">
        <div class="container">
            <div class="head">
                <div class="pull-left">
                    <g:link uri="/">
                        <img id="logo" src="${resource(dir: 'images', file: 'redhat.png')}"/>
                    </g:link>
                    %{--<g:form name="search-field" method="get" controller="search" action="index">
                        <g:textField value="${params.q}" name="q" placeholder="${message(code: 'search.label')}"/>
                        <button type="submit" class="btn-link">
                            <i class="icon-search"></i>
                        </button>
                    </g:form>--}%
                </div>
                <div class="pull-right">
                    <sec:ifLoggedIn>
                        <g:link controller="profile"><sec:loggedInUserInfo field="fullName"/></g:link>
                        <div class="tms-usermenu">
                            <notification:render/>
                            <div class="dropdown" style="display: inline;">
                                <a class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="icon-cog large"></i>
                                </a>
                                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                    <li>
                                        <g:link tabindex="-1" controller="profile" action="edit"><g:message code="profile.edit.button"/></g:link>
                                    </li>
                                    <sec:ifAnyGranted roles="ROLE_ADMIN">
                                    <li id="configuration">
                                        <g:link controller="configuration"><g:message code="config.edit.button"/></g:link>
                                    </li>
                                    </sec:ifAnyGranted>
                                    <li class="divider"></li>
                                    <li>
                                        <g:link tabindex="-1" controller="logout"><g:message code="security.logout.button"/></g:link>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <g:link controller="login" action="auth"><g:message code="security.login.button"/></g:link>
                        <i class="icon-circle mini"></i>
                        <g:link controller="registration"><g:message code="registration.create.button"/></g:link>
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
                <div class="span4">
                    <p class="leading"><g:message code="footer.account.title"/></p>
                    <g:link controller="termsOfUse"><g:message code="footer.termsOfUse.label"/></g:link>
                    <g:link controller="faq" action="list"><g:message code="footer.faq.label"/></g:link>
                </div>
                <div class="span4">
                    <p class="leading"><g:message code="footer.getInvolved.title"/></p>
                    <a href="https://github.com/jcechace/Thesis-management-system">
                        <g:message code="footer.github.label"/>
                    </a>
                    <a href="https://github.com/jcechace/Thesis-management-system/issues">
                        <g:message code="footer.issues.label"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="footer-down">
            <div class="container">
                <p class="pull-right">Copyright &copy; 2013 Red Hat, Inc.</p>
            </div>
        </div>
    </footer>
    <r:layoutResources />
</body>
</html>
