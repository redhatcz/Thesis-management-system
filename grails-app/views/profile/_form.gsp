<%@ page import="com.redhat.theses.auth.User" %>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'fullName', 'error')} required">
    <label class="control-label" for="fullName">
        <strong><g:message code="user.fullName.label" default="Full name" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="fullName" placeholder="Full name" value="${profileCommand?.fullName}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'email', 'error')} required">
    <label class="control-label" for="email">
        <strong><g:message code="user.email.label" default="Email" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="email" placeholder="Email" value="${profileCommand?.email}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'repeatEmail', 'error')}">
    <label class="control-label" for="repeatEmail">
        <strong><g:message code="user.repeatEmail.label" default="Repeat Email" /></strong>
    </label>
    <div class="controls">
        <g:textField name="repeatEmail" placeholder="Repeat email" value="${profileCommand?.repeatEmail}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'password', 'error')}">
    <label class="control-label" for="password">
        <strong><g:message code="user.password.label" default="Password" /></strong>
    </label>
    <div class="controls">
        <g:passwordField name="password" placeholder="Password" value="${profileCommand?.password}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'repeatPassword', 'error')}">
    <label class="control-label" for="repeatPassword">
        <strong><g:message code="user.repeatPassword.label" default="Repeat password" /></strong>
    </label>
    <div class="controls">
        <g:passwordField name="repeatPassword" placeholder="Repeat password" value="${profileCommand?.repeatPassword}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'oldPassword', 'error')} required">
    <label class="control-label" for="repeatPassword">
        <strong><g:message code="user.oldPassword.label" default="Old password" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:passwordField name="oldPassword" placeholder="Old password" value="${profileCommand?.repeatPassword}"/>
    </div>
</div>
