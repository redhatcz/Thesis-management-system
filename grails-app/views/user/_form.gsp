<%@ page import="com.redhat.theses.auth.Role" %>
<div class="control-group ${hasErrors(bean: userInstance, field: 'email', 'error')} required">
    <label class="control-label" for="user.email">
        <strong><g:message code="user.email.label"/>
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="user.email" value="${userInstance?.email}"
                     placeholder="${message(code: 'user.email.label')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'fullName', 'error')} required">
	<label class="control-label" for="user.fullName">
		<strong><g:message code="user.fullName.label"/>
		<span class="required-indicator">*</span></strong>
	</label>
    <div class="controls">
	    <g:textField name="user.fullName" value="${userInstance?.fullName}"
                     placeholder="${message(code: 'user.fullName.label')}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'roles', 'error')}">
    <label class="control-label" for="user.roles">
        <strong><g:message code="user.roles.label"/>
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:select name="user.roles" from="${Role.values()}" multiple="multiple"
                  size="4" value="${userInstance?.roles}" class="many-to-many"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
    <div class="controls">
        <label class="checkbox" for="user.enabled">
            <g:checkBox name="user.enabled" value="${userInstance?.enabled}" />
            <g:message code="user.enabled.label" default="Enabled" />
        </label>
    </div>
    <div class="controls">
        <label class="checkbox" for="user.passwordExpired">
            <g:checkBox name="user.passwordExpired" value="${userInstance?.passwordExpired}" />
            <g:message code="user.passwordExpired.label"/>
        </label>
    </div>
    <div class="controls">
        <label class="checkbox" for="user.accountLocked">
            <g:checkBox name="user.accountLocked" value="${userInstance?.accountLocked}" />
            <g:message code="user.accountLocked.label"/>
        </label>
    </div>
    <div class="controls">
        <label class="checkbox" for="user.accountExpired">
            <g:checkBox name="user.accountExpired" value="${userInstance?.accountExpired}" />
            <g:message code="user.accountExpired.label"/>
        </label>
    </div>
</div>
