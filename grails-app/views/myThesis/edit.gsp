<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Thesis')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<h1 class="header">
    <g:message code="default.edit.label" args="[entityName]" />
</h1>
<g:form class="form-horizontal" method="post" name="thesis-form">
    <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
    <g:hiddenField name="thesis.version" value="${thesisInstance?.version}" />
    <g:render template="formEdit"/>

    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="btn btn-primary" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </div>
    </div>
</g:form>


<g:form class="form-horizontal" method="post" name="thesis-form">
    <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
    <g:hiddenField name="thesis.version" value="${thesisInstance?.version}" />
    <g:render template="formFiles"/>
</g:form>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="btn btn-primary" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </div>
    </div>
</body>
</html>
