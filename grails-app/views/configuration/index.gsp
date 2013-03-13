<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="config.edit.title" /></title>
</head>
<body>
<h2 class="header"><g:message code="config.edit.header" /></h2>
<g:form class="form-inline" method="post" name="thesis-form">
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn"
                            action="update"
                            value="${message(code: 'default.update.button')}" />
            <g:link class="tms-btn tms-danger"
                    onclick="return confirm('${message(code: 'default.delete.confirm.message')}');">${message(code: 'default.cancel.button')}</g:link>
        </div>
    </div>
</g:form>
</body>
</html>
