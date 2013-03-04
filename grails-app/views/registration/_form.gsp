<%@ page import="com.redhat.theses.auth.User" %>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'fullName', 'error')} required">
    <label class="control-label" for="fullName">
        <strong><g:message code="user.fullName.label" default="Full name" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="fullName" placeholder="Full name" value="${registrationCommand?.fullName}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'email', 'error')} required">
    <label class="control-label" for="email">
        <strong><g:message code="user.email.label" default="Email" />
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="email" placeholder="Email" value="${registrationCommand?.email}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'repeatEmail', 'error')} required">
    <label class="control-label" for="repeatEmail">
        <strong><g:message code="user.repeatEmail.label" default="Repeat Email" />
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="repeatEmail" placeholder="Repeat email" value="${registrationCommand?.repeatEmail}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'password', 'error')} required">
    <label class="control-label" for="password">
        <strong><g:message code="user.password.label" default="Password" />
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:passwordField name="password" placeholder="Password" value="${registrationCommand?.password}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'repeatPassword', 'error')} required">
    <label class="control-label" for="repeatPassword">
        <strong><g:message code="user.repeatPassword.label" default="Repeat password" />
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:passwordField name="repeatPassword" placeholder="Repeat password" value="${registrationCommand?.repeatPassword}"/>
    </div>
</div>

