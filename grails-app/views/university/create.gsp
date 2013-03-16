<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="university.create.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="university.create.header"/>
        <small class="pull-right">
            <g:link controller="university">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:render template="/shared/create"/>
</body>
</html>
