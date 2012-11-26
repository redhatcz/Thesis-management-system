<%@ page import="com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="light">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<h1 class="header">
    <g:message code="default.edit.label" args="[entityName]" />
</h1>
<g:form class="form-horizontal" method="post" >
    <div class="control-group ${hasErrors(bean: passwordCommand, field: 'oldPassword', 'error')} required">
        <label class="control-label" for="oldPassword">
            <g:message code="user.oldPassword.label" default="Old password" />
            <span class="required-indicator">*</span>
        </label>
        <div class="controls">
            <g:passwordField name="oldPassword" placeholder="Old password" value="${passwordCommand?.oldPassword}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: passwordCommand, field: 'password', 'error')} required">
        <label class="control-label" for="password">
            <g:message code="user.password.label" default="Password" />
            <span class="required-indicator">*</span>
        </label>
        <div class="controls">
            <g:passwordField name="password" placeholder="Password" value="${passwordCommand?.password}"/>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: passwordCommand, field: 'repeatPassword', 'error')} required">
        <label class="control-label" for="repeatPassword">
            <g:message code="user.repeatPassword.label" default="Repeat password" />
            <span class="required-indicator">*</span>
        </label>
        <div class="controls">
            <g:passwordField name="repeatPassword" placeholder="Repeat password" value="${passwordCommand?.repeatPassword}"/>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <g:actionSubmit class="btn btn-primary" action="updatePassword"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:link class="btn btn-danger" controller="profile"
                    onclick="return confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Are you sure?')}');">
                <g:message code="default.button.cancel.label" default="Cancel"/>
            </g:link>
        </div>
    </div>
</g:form>
</body>
</html>