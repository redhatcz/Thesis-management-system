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
        <richg:multiselect name="user.roles" from="${Role.values()}" value="${userInstance?.roles}" size="4"
                           optionValue="${{g.message(code:"role.${it?.toString()?.toLowerCase()}.label")}}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
    <div class="controls">
        <label class="checkbox" for="user.accountLocked">
            <g:checkBox name="user.accountLocked" value="${userInstance?.accountLocked}" />
            <g:message code="user.accountLocked.label"/>
        </label>
    </div>
</div>
