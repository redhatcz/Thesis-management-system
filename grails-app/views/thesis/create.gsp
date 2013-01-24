<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <h1 class="header">
        <g:message code="default.create.label" args="[entityName]" />
    </h1>
    <g:form class="form-horizontal" action="save" >
        <div class="control-group ${hasErrors(bean: thesisInstance?.topic, field: 'title', 'error')}">
            <label class="control-label" for="thesis.topic.title">
                <g:message code="thesis.topic.title.label" default="Topic" />
            </label>
            <div class="controls">
                <g:hiddenField name="thesis.topic.id" value="${thesisInstance?.topic?.id}"/>
                <a4g:textField name="thesis.topic.title" value="${thesisInstance?.topic?.title}" required=""
                               autocomplete-url="${createLink(controller: 'json', action: 'listTopicsByTitle')}"
                               autocomplete-target="thesis.topic.id"/>
            </div>
        </div>

        <div class="control-group ${hasErrors(bean: thesisInstance?.assignee, field: 'fullName', 'error')}">
            <label class="control-label" for="thesis.assignee.fullName">
                <g:message code="thesis.assignee.label" default="Assignee" />
            </label>
            <div class="controls">
                <g:hiddenField name="thesis.assignee.id" value="${thesisInstance?.assignee?.id}"/>
                <a4g:textField name="thesis.assignee.fullName" value="${thesisInstance?.assignee?.fullName}" required=""
                               autocomplete-url="${createLink(controller: 'json', action: 'listUsersByName')}"
                               autocomplete-target="thesis.assignee.id"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <g:submitButton name="create" class="btn btn-primary"
                                value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </div>
        </div>
    </g:form>
</body>
</html>