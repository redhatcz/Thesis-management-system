<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'Supervision.label', default: 'Supervisions')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<h1 class="header">
    <g:message code="default.edit.label" args="[entityName]" />
</h1>
<g:form class="form-horizontal" method="post">
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn" action="save"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </div>
    </div>
</g:form>
</body>
</html>
