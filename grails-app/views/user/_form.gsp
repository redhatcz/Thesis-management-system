<%@ page import="com.redhat.theses.auth.User" %>
<div class="control-group ${hasErrors(bean: userInstance, field: 'email', 'error')} required">
    <label class="control-label" for="user.email">
        <strong><g:message code="user.email.label" default="Email" />
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="user.email" value="${userInstance?.email}"
                     placeholder="${message(code: 'user.email', default: 'Email')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'fullName', 'error')} required">
	<label class="control-label" for="user.fullName">
		<strong><g:message code="user.fullName.label" default="Full Name" />
		<span class="required-indicator">*</span></strong>
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
