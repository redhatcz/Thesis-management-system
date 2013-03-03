<%@ page import="com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="default.edit.label" args="[entityName]" />
    </h2>
    <g:form class="form-inline" method="post" >
        <g:hiddenField name="user.id" value="${userInstance?.id}" />
        <g:hiddenField name="user.version" value="${userInstance?.version}" />
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn" action="update"
                                value="${message(code: 'default.button.update.label', default: 'Update')}" />
                <g:actionSubmit class="tms-btn tms-danger" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </div>
        </div>
    </g:form>
</body>
</html>
