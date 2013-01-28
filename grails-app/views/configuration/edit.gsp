<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="configuration.edit.title" default="Edit configuration" /></title>
</head>
<body>
<h1 class="header">
    <g:message code="configuration.edit.header" default="Edit configuration" />
</h1>
<g:form class="form-horizontal" method="post" name="thesis-form">
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="btn btn-primary" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </div>
    </div>
</g:form>
</body>
</html>