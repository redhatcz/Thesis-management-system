<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span12">
        <h1 class="header">
            <g:message code="default.edit.label" args="[entityName]" />
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
        <g:form class="form-horizontal" method="post" >
            <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
            <g:hiddenField name="topic.version" value="${topicInstance?.version}" />
            <fieldset>
                <g:render template="form"/>
            </fieldset>
            <div class="control-group">
                <div class="controls">
                <g:actionSubmit class="btn btn-primary" action="update"
                                value="${message(code: 'default.button.update.label', default: 'Update')}" />
                <g:actionSubmit class="btn btn-danger" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </div>
            </div>
        </g:form>
    </div>
</body>
</html>
