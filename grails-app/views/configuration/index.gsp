<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="config.edit.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="config.edit.header" />
        <small class="pull-right">
            <g:link uri="/">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:form class="form-inline" method="post" name="thesis-form">
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn btn-large" id="conf-update-btn" action="update"
                                value="${message(code: 'default.update.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
