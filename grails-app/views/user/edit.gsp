<%@ page import="com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="user.edit.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="user.edit.header" />
        <small class="pull-right">
            <g:link action="show" id="${userInstance?.id}">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:form class="form-inline" method="post" >
        <g:hiddenField name="user.id" value="${userInstance?.id}" />
        <g:hiddenField name="user.version" value="${userInstance?.version}" />
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn btn-large" action="update"
                                value="${message(code: 'default.update.button')}" />
                <g:actionSubmit class="tms-btn tms-danger btn-large"
                                action="delete"
                                value="${message(code: 'default.delete.button')}"
                                onclick="return confirm('${message(code: 'default.delete.confirm.message')}');" />
            </div>
        </div>
    </g:form>
</body>
</html>
