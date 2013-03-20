<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="thesis.create.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="thesis.create.header" />
        <small class="pull-right">
            <g:link controller="thesis">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:form class="form-inline" action="save" >
        <g:render template="/shared/thesis/formCreate"/>
        <div class="control-group">
            <div class="controls">
                <g:submitButton name="create"
                                class="tms-btn btn-large"
                                value="${message(code: 'default.create.button')}" />
            </div>
        </div>
    </g:form>
</body>
</html>
