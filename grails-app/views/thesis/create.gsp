<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <h2 class="header"><g:message code="default.create.label" args="[entityName]" /></h2>
    <g:form class="form-inline" action="save" >
        <g:render template="formCreate"/>
        <div class="control-group">
            <div class="controls">
                <g:submitButton name="create"
                                class="tms-btn"
                                value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
