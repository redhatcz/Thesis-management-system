<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<g:applyLayout name="main">
<html>
<head>
    <title><g:layoutTitle /></title>
    <g:layoutHead/>
</head>
<body>
<div class="span4 sidebar">
    <div class="panel left">
        <div class="avatar">
            %{-- TODO: Profile picture will be here soon! --}%
        </div>
        <h4><g:message code="user.information.label"/></h4>
        <div class="panel-content">
            <dl>
                <dt>
                    <i class="icon-envelope-alt"></i>
                    ${message(code: 'user.email.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:fieldValue bean="${userInstance}" field="email"/>
                </dd>
                <dt>
                    <i class="icon-user"></i>
                    ${message(code: 'user.fullName.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:fieldValue bean="${userInstance}" field="fullName"/>
                </dd>
                <dt>
                    <i class="icon-time"></i>
                    ${message(code: 'user.dateCreated.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:formatDate date="${userInstance?.dateCreated}" dateStyle="LONG" type="date" />
                </dd>

                <g:if test="${userInstance?.enabled}">
                <dt>
                    <i class="icon-lock"></i>
                    ${message(code: 'user.enabled.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:formatBoolean boolean="${userInstance?.enabled}" />
                </dd>
                </g:if>

                <g:if test="${userInstance?.accountExpired}">
                <dt>
                    <i class="icon-time"></i>
                    ${message(code: 'user.accountExpired.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:formatBoolean boolean="${userInstance?.accountExpired}" />
                </dd>
                </g:if>
                <g:if test="${userInstance?.accountLocked}">
                <dt>
                    <i class="icon-lock"></i>
                    ${message(code: 'user.accountLocked.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:formatBoolean boolean="${userInstance?.accountLocked}" />
                </dd>
                </g:if>
            </dl>
        </div>
    </div>
</div>
<div class="span8 content">
    <ul class="nav nav-tabs">
        <li class="${pageProperty(name: 'page.active-button') == 'theses' ? 'active' : ''}">
            <g:link action="show" id="${userInstance?.id}"><i class="icon-book"></i>Theses</g:link>
        </li>
        <li class="${pageProperty(name: 'page.active-button') == 'activity' ? 'active' : ''}">
            <g:link action="activity" id="${userInstance?.id}"><i class="icon-eye-open"></i>Activity</g:link>
        </li>
        <div class="controls pull-right">
            <sec:ifAllGranted roles="ROLE_ADMIN">
            <g:link class="tms-btn" controller="user"
                    action="edit" id="${userInstance?.id}"
                ><i class="icon-wrench"></i><g:message code="default.edit.button" /></g:link>
            </sec:ifAllGranted>

            <sec:ifLoggedIn>
                <g:if test="${sec.loggedInUserInfo(field: 'id')?.toLong() == userInstance?.id}">
                <g:link class="tms-btn" controller="profile" action="edit"
                    ><i class="icon-wrench"></i><g:message code="profile.edit.button" /></g:link>
                </g:if>
            </sec:ifLoggedIn>
        </div>
    </ul>
    <div>
        <g:pageProperty name="page.main-box"/>
    </div>
</div>
</body>
</html>
</g:applyLayout>
