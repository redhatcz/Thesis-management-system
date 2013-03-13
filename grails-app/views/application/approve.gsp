<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="application.approve.title"/></title>
</head>
<body>
<h2 class="header"><g:message code="application.approve.header"/></h2>
<g:form class="form-inline" action="approveSave" id="${applicationInstance?.id}">
    <g:render template="/shared/thesis/formCreate"/>
    <div class="control-group">
        <div class="controls">
            <g:submitButton name="create" class="tms-btn"  value="${message(code: 'default.create.button')}" />
        </div>
    </div>
</g:form>
</body>
</html>