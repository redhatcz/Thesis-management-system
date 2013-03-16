<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="registration.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="registration.header" />
        <small class="pull-right">
            <g:link uri="/">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:form class="form-inline" action="register" >
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit name="register"
                                class="tms-btn btn-large"
                                value="${message(code: 'registration.register.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
