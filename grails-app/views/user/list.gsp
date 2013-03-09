<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8 content">
        <h1 class="header"><g:message code="default.list.label" args="[entityName]" /></h1>
        <table class="table">
            <thead>
                <tr>
                    <g:sortableColumn property="id" title="${message(code: 'user.id.label', default: 'Id')}" />
                    <g:sortableColumn property="fullName" title="${message(code: 'user.fullName.label', default: 'Full Name')}" />
                    <g:sortableColumn property="dateCreated" title="${message(code: 'user.dateCreated.label', default: 'Registration date')}" />
                </tr>
            </thead>
            <tbody>
            <g:each in="${userInstanceList}" status="i" var="userInstance">
                <tr>
                    <td><g:fieldValue field="id" bean="${userInstance}"/></td>
                    <td><g:link action="show" id="${userInstance.id}"><g:fieldValue bean="${userInstance}" field="fullName"/></g:link></td>
                    <td><g:formatDate date="${userInstance?.dateCreated}" dateStyle="LONG" type="date" /></td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <g:if test="${Util.isPaginationVisible(userInstanceTotal, params.max)}">
            <g:paginate total="${userInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4>Manage Users</h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
            </div>
        </div>
    </div>
</body>
</html>
