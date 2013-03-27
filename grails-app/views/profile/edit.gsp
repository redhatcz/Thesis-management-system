<%@ page import="com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="profile.edit.title"/></title>
</head>
<body>
    <h2 class="header">
        <g:message code="profile.edit.header" />
        <small class="pull-right">
            <g:link action="index">
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
            </div>
        </div>
    </g:form>
</body>
</html>
