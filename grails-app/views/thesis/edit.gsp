<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.edit.title" /></title>
</head>
<body>
<h2 class="header"><g:message code="thesis.edit.header" /></h2>
<g:form class="form-inline" method="post" name="thesis-form">
    <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
    <g:hiddenField name="thesis.version" value="${thesisInstance?.version}" />
    <g:hiddenField name="thesis.topic.id" value="${thesisInstance?.topic?.id}" />
    <g:render template="formEdit"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn"
                            action="update"
                            value="${message(code: 'default.update.button')}" />
            <g:link class="tms-btn tms-danger"
                    action="show"
                    id="${thesisInstance?.id}"
                    onclick="return confirm('${message(code: 'default.delete.confirm.message')}');">${message(code: 'default.cancel.button')}</g:link>
        </div>
    </div>
</g:form>
</body>
</html>
