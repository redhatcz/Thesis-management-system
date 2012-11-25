<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'application.label', default: 'Application')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <g:render template="/shared/create"/>
</body>
</html>
