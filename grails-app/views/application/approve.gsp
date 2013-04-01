<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="application.approve.title"/></title>
</head>
<body>
<h2 class="header"><g:message code="application.approve.header"/></h2>
<g:form class="form-inline" action="approveSave" id="${applicationInstance?.id}">
    <g:hiddenField name="thesis.topic.id" value="${applicationInstance?.topicId}"/>
    <g:render template="/thesis/form"/>
    <div class="control-group">
        <div class="controls">
            <g:submitButton name="create" class="tms-btn btn-large"
                            value="${message(code: 'default.create.button')}" />
        </div>
    </div>
</g:form>
</body>
</html>
