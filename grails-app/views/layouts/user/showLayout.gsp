<%@ page import="com.redhat.theses.auth.Role; com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<g:applyLayout name="main">
<html>
<head>
    <title><g:layoutTitle /></title>
    <g:layoutHead/>
</head>
<body>
<div class="span4 sidebar">
    <div class="panel left">
        <g:render template="/layouts/user/avatar"/>
        <h4><g:message code="user.information.label"/></h4>
        <div class="panel-content">
            <dl>
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.email.label').toString()}">
                    <i class="icon-envelope"></i>
                </dt>
                <dd>
                    <!-- let's make it really hard for crawlers by first reversing the text and replacing
                         at character with html entity :) -->
                    <bdo dir="rtl">${userInstance?.email?.reverse()?.replaceAll('\\@', '&#64;')}</bdo>
                </dd>
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.fullName.label').toString()}">
                    <i class="icon-user"></i>
                </dt>
                <dd>
                    <g:fieldValue bean="${userInstance}" field="fullName"/>
                </dd>
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.dateCreated.label').toString()}">
                    <i class="icon-time"></i>
                </dt>
                <dd>
                    <g:formatDate date="${userInstance?.dateCreated}" dateStyle="LONG" type="date" />
                </dd>
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.role.label').toString()}">
                    <i class="icon-group"></i>
                </dt>
                <dd>
                    <g:message code="role.${Role.getHighest(userInstance?.roles).toString()?.toLowerCase()}.label"/>
                </dd>

                <g:if test="${!userInstance?.enabled}">
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.enabled.label').toString()}">
                    <i class="icon-lock"></i>
                </dt>
                <dd>
                    <g:formatBoolean boolean="${userInstance?.enabled}" />
                </dd>
                </g:if>

                <g:if test="${userInstance?.accountExpired}">
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.accountExpired.label').toString()}">
                    <i class="icon-time"></i>
                </dt>
                <dd>
                    <g:formatBoolean boolean="${userInstance?.accountExpired}" />
                </dd>
                </g:if>
                <g:if test="${userInstance?.accountLocked}">
                <dt class="tms-tooltip" data-placement="left"
                    data-original-title="${message(code: 'user.accountLocked.label').toString()}">
                    <i class="icon-lock"></i>
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
        <li class="${pageProperty(name: 'page.active-button') == 'main' ? 'active' : ''}">
            <g:link action="show" id="${userInstance?.id}">
                <i class="icon-book"></i> <g:message code="user.main.button"/>
            </g:link>
        </li>
        <li class="${pageProperty(name: 'page.active-button') == 'activity' ? 'active' : ''}">
            <g:link action="activity" id="${userInstance?.id}">
                <i class="icon-eye-open"></i> <g:message code="user.activity.label"/>
            </g:link>
        </li>
        <sec:ifLoggedIn>
        <g:if test="${sec.loggedInUserInfo(field: 'id')?.toLong() == userInstance?.id}">
            <li class="${pageProperty(name: 'page.active-button') == 'applications' ? 'active' : ''}">
                <g:link action="applications" id="${userInstance?.id}">
                    <i class="icon-file"></i> <g:message code="user.applications.label"/>
                </g:link>
            </li>
        </g:if>
        </sec:ifLoggedIn>
        <div class="controls pull-right">
            <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:link class="tms-btn" controller="user" action="edit" id="${userInstance?.id}">
                <i class="icon-wrench"></i> <g:message code="default.edit.button" />
            </g:link>
            </sec:ifAnyGranted>

            <sec:ifLoggedIn>
                <g:if test="${sec.loggedInUserInfo(field: 'id')?.toLong() == userInstance?.id}">
                <g:link class="tms-btn" controller="profile" action="edit">
                    <i class="icon-wrench"></i> <g:message code="profile.edit.button" />
                </g:link>
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
