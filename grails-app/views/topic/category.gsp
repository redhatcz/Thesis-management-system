<!DOCTYPE html>
<html>
<head>
    %{--TODO: fill in correct title--}%
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <g:render template="topicList" />
</body>
</html>
