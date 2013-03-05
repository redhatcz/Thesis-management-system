<%@ page import="com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName"
           value="${message(code: 'organization.label', default: 'Organization')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <g:render template="/shared/create"/>
</body>
</html>
