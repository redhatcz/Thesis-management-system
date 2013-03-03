<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="configuration.edit.title" default="Edit configuration" /></title>
</head>
<body>
<h2 class="header">
    <g:message code="configuration.edit.header" default="Edit configuration" />
</h2>
<g:form class="form-inline" method="post" name="thesis-form">
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:link class="tms-btn tms-danger" action="index"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                ${message(code: 'default.button.cancel.label', default: 'Cancel')}
            </g:link>
        </div>
    </div>
</g:form>
</body>
</html>
