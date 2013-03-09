<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="title" value="${message(code: 'application.from.thesis.label', default: 'Create new thesis from application')}" />
    <title>${title}</title>
</head>
<body>
<h2 class="header">${title}</h2>
<g:form class="form-inline" action="approveSave" id="${applicationInstance?.id}">
    <g:render template="/shared/thesis/formCreate"/>
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