<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="registration.title" /></title>
</head>
<body>
    <h2 class="header"><g:message code="registration.header" /></h2>
    <g:form class="form-inline" action="register" >
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit name="register"
                                class="tms-btn"
                                value="${message(code: 'registration.register.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
