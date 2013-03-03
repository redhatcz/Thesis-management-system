<%@ page import="com.redhat.theses.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<h2 class="header">
    <g:message code="default.edit.label" args="[entityName]" />
</h2>
<g:form class="form-inline" method="post" >
    <g:hiddenField name="user.id" value="${tagInstance?.id}" />
    <g:hiddenField name="user.version" value="${tagInstance?.version}" />
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="tms-btn" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:link class="tms-btn tms-danger" action="show" id="${tagInstance?.id}"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                ${message(code: 'default.button.cancel.label', default: 'Cancel')}
            </g:link>
        </div>
    </div>
</g:form>
</body>
</html>
