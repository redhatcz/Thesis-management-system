<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.edit.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="thesis.edit.header" />
        <small class="pull-right">
            <g:link action="show" id="${thesisInstance?.id}">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:form class="form-inline" method="post" name="thesis-form">
        <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
        <g:hiddenField name="thesis.version" value="${thesisInstance?.version}" />
        <g:hiddenField name="thesis.topic.id" value="${thesisInstance?.topic?.id}" />

        <g:render template="form"/>

        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn btn-large"
                                action="update"
                                value="${message(code: 'default.update.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
