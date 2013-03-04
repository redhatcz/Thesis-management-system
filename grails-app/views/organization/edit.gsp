<%@ page import="com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
    <h2 class="header"><g:message code="default.edit.label" args="[entityName]" /></h2>
    <g:form class="form-inline" method="post" >
        <g:hiddenField name="organization.id" value="${organizationInstance?.id}" />
        <g:hiddenField name="organization.version" value="${organizationInstance?.version}" />
        <g:render template="form"/>
        <div class="control-group">
            <div class="controls">
                <g:actionSubmit class="tms-btn" action="update"
                                value="${message(code: 'default.button.update.label', default: 'Update')}" />
                <g:link class="tms-btn tms-danger" action="show" id="${organizationInstance?.id}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                    ${message(code: 'default.button.cancel.label', default: 'Cancel')}
                </g:link>
            </div>
        </div>
    </g:form>
</body>
</html>
