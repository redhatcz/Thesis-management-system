<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="supervision.manage.title"/></title>
</head>
<body>
    <h2 class="header"><g:message code="supervision.manage.header"/></h2>
    <g:form class="form-inline" method="post">
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn btn-large"
                                action="save"
                                value="${message(code: 'default.update.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
