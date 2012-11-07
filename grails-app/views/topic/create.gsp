<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span12">
        <h1 class="header">
            <g:message code="default.create.label" args="[entityName]" />
        </h1>
        <g:hasErrors>
        <ul class="errors" role="alert">
            <g:eachError var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}"/>
                </li>
            </g:eachError>
        </ul>
        </g:hasErrors>
        <g:form class="form-horizontal" action="save" >
            <fieldset class="form">
                <g:render template="form"/>
            </fieldset>
            <div class="control-group">
                <div class="controls">
                    <g:submitButton name="create" class="btn btn-primary"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>
            </div>
        </g:form>
    </div>
</body>
</html>
