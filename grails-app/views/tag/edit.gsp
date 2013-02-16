<%@ page import="com.redhat.theses.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<h1 class="header">
    <g:message code="default.edit.label" args="[entityName]" />
</h1>
<g:form class="form-horizontal" method="post" >
    <g:hiddenField name="user.id" value="${tagInstance?.id}" />
    <g:hiddenField name="user.version" value="${tagInstance?.version}" />
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:actionSubmit class="tms-btn tms-danger" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </div>
    </div>
</g:form>
</body>
</html>
