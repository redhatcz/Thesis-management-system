<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <title><g:message code="faq.create.title" /></title>
</head>
<body>
    <h2 class="header">
        <g:message code="faq.create.header"/>
        <small class="pull-right">
            <g:link controller="faq">
                <i class="icon-double-angle-left"></i>
                ${message(code: 'default.back.button')}
            </g:link>
        </small>
    </h2>
    <g:render template="/shared/create"/>
</body>
</html>
