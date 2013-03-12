<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.create.title" /></title>
</head>
<body>
    <h2 class="header"><g:message code="thesis.create.header" /></h2>
    <g:form class="form-inline" action="save" >
        <g:render template="/shared/thesis/formCreate"/>
        <div class="control-group">
            <div class="controls">
                <g:submitButton name="create"
                                class="tms-btn"
                                value="${message(code: 'default.create.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
