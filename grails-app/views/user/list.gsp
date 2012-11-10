
<%@ page import="com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8">
        <h1 class="header">
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <table class="table">
            <thead>
                <tr>
                    <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />
                    <g:sortableColumn property="fullName" title="${message(code: 'user.fullName.label', default: 'Full Name')}" />
                    <g:sortableColumn property="accountExpired" title="${message(code: 'user.accountExpired.label', default: 'Account Expired')}" />
                    <g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}" />
                    <g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled')}" />
                </tr>
            </thead>
            <tbody>
            <g:each in="${userInstanceList}" status="i" var="userInstance">
                <tr>
                    <td><g:link action="show" id="${userInstance.id}">
                        ${fieldValue(bean: userInstance, field: "username")}
                    </g:link></td>
                    <td>${fieldValue(bean: userInstance, field: "fullName")}</td>
                    <td><g:formatBoolean boolean="${userInstance.accountExpired}" /></td>
                    <td><g:formatBoolean boolean="${userInstance.accountLocked}" /></td>
                    <td><g:formatBoolean boolean="${userInstance.enabled}" /></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="span4">
        <div class="thesis">
            <h4>Manage Users</h4>
            <g:link class="btn btn-info" action="create">
                <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </div>
    </div>
</body>
</html>
