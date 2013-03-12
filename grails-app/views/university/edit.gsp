<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="university.edit.title" /></title>
</head>
<body>
    <h2 class="header"><g:message code="university.edit.header" /></h2>
    <g:form class="form-inline" method="post" >
        <g:hiddenField name="university.id" value="${universityInstance?.id}" />
        <g:hiddenField name="university.version" value="${universityInstance?.version}" />
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn"
                                action="update"
                                value="${message(code: 'default.update.button')}" />
                <g:link class="tms-btn tms-danger"
                        action="show"
                        id="${universityInstance?.id}"
                        onclick="return confirm('${message(code: 'default.delete.confirm.message')}');">${message(code: 'default.cancel.button')}</g:link>
            </div>
        </div>
    </g:form>
</body>
</html>
