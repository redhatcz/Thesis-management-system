<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="default.registration.label" default="Registration" /></title>
</head>
<body>
<div id="registration" class="content scaffold-create" role="main">
    <h1><g:message code="default.registration.label" default="Registration" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors>
        <ul class="errors" role="alert">
            <g:eachError var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="register" >
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="register" class="register" value="${message(code: 'default.button.register.label', default: 'Register')}" />
        </fieldset>
    </g:form>
</div>
</body>
</html>