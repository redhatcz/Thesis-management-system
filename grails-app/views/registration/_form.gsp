<%@ page import="com.redhat.theses.auth.User" %>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'username', 'error')} required">
    <label class="control-label" for="username">
        <g:message code="user.username.label" default="Username" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:textField name="username" placeholder="Username" value="${registrationCommand?.username}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'password', 'error')} required">
    <label class="control-label" for="password">
        <g:message code="user.password.label" default="Password" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:passwordField name="password" placeholder="Password" value="${registrationCommand?.password}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'password', 'error')} required">
    <label class="control-label" for="repeatPassword">
        <g:message code="user.repeatPassword.label" default="Repeat password" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:passwordField name="repeatPassword" placeholder="Repeat password" value="${registrationCommand?.repeatPassword}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'fullName', 'error')} required">
    <label class="control-label" for="fullName">
        <g:message code="user.fullName.label" default="Full name" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:textField name="fullName" placeholder="Full name" value="${registrationCommand?.fullName}"/>
    </div>
</div>

