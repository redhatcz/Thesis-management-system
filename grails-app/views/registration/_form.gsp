<%@ page import="com.redhat.theses.auth.User" %>

<div class="fieldcontain ${hasErrors(bean: registrationCommand, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="user.username.label" default="Username" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" required="" value="${registrationCommand?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationCommand, field: 'password', 'error')} required">
    <label for="password">
        <g:message code="user.password.label" default="Password" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="password" required="" value="${registrationCommand?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationCommand, field: 'password', 'error')} required">
    <label for="repeatPassword">
        <g:message code="user.repeatPassword.label" default="Repeat password" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="repeatPassword" required="" value="${registrationCommand?.repeatPassword}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationCommand, field: 'fullName', 'error')} required">
    <label for="fullName">
        <g:message code="user.fullName.label" default="Full name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fullName" required="" value="${registrationCommand?.fullName}"/>
</div>

