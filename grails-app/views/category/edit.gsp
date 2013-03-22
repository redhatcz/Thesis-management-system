<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="category.edit.title"/></title>
</head>
<body>
<h2 class="header"><g:message code="category.edit.header"/></h2>
<g:form class="form-inline" method="post" >
    <g:hiddenField name="category.id" value="${categoryInstance?.id}" />
    <g:hiddenField name="category.version" value="${categoryInstance?.version}" />
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn btn-large" action="update" value="${message(code: 'default.update.button')}" />
        </div>
    </div>
</g:form>
</body>
</html>
