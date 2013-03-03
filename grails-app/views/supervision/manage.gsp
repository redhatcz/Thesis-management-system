<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'Supervision.label', default: 'Supervisions')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="default.edit.label" args="[entityName]" />
    </h2>
    <g:form class="form-inline" method="post">
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
