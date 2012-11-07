<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="default.registration.label" default="Registration" /></title>
</head>
<body>
    <h1 class="header">
        <g:message code="default.registration.label" default="Registration" />
    </h1>
    <div class="offset2">
        <g:form class="form-horizontal" action="register" >
            <fieldset class="form">
                <g:render template="form"/>
            </fieldset>
            <div class="control-group">
                <div class="controls">
                    <g:actionSubmit name="register" class="btn btn-primary"
                                    value="${message(code: 'default.button.register.label', default: 'Register')}" />
                </div>
            </div>
        </g:form>
    </div>
</body>
</html>