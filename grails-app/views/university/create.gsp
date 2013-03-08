<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName"
           value="${message(code: 'univrsity.label', default: 'University')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <g:render template="/shared/create"/>
</body>
</html>
