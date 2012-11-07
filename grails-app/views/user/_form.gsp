<%@ page import="com.redhat.theses.auth.User" %>
<div class="control-group ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label class="control-label" for="user.username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
    <div class="controls">
	    <g:textField name="user.username" value="${userInstance?.username}"
	                 placeholder="${message(code: 'user.username', default: 'Username')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label class="control-label" for="user.password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
    <div class="controls">
	    <g:passwordField name="user.password" value="${userInstance?.password}"
                         placeholder="${message(code: 'user.password.label', default: 'Password')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'fullName', 'error')} required">
	<label class="control-label" for="user.fullName">
		<g:message code="user.fullName.label" default="Full Name" />
		<span class="required-indicator">*</span>
	</label>
    <div class="controls">
	    <g:textField name="user.fullName" value="${userInstance?.fullName}"
                     placeholder="${message(code: 'user.fullName.label', default: 'Full Name')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
    <div class="controls">
        <label class="checkbox" for="user.enabled">
            <g:checkBox name="user.enabled" value="${userInstance?.enabled}" />
            <g:message code="user.enabled.label" default="Enabled" />
        </label>
        <label class="checkbox" for="user.passwordExpired">
            <g:checkBox name="user.passwordExpired" value="${userInstance?.passwordExpired}" />
            <g:message code="user.passwordExpired.label" default="Password Expired" />
        </label>
        <label class="checkbox" for="user.accountLocked">
            <g:checkBox name="user.accountLocked" value="${userInstance?.accountLocked}" />
            <g:message code="user.accountLocked.label" default="Account Locked" />
        </label>
        <label class="checkbox" for="user.accountExpired">
            <g:checkBox name="user.accountExpired" value="${userInstance?.accountExpired}" />
            <g:message code="user.accountExpired.label" default="Account Expired" />
        </label>
    </div>
</div>

