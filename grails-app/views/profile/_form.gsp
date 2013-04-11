<%@ page import="com.redhat.theses.auth.User" %>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'fullName', 'error')} required">
    <label class="control-label" for="fullName">
        <strong><g:message code="user.fullName.label" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="fullName" placeholder="${message(code: 'user.fullName.label')}" value="${profileCommand?.fullName}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'email', 'error')} required">
    <label class="control-label" for="email">
        <strong><g:message code="user.email.label" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="email" placeholder="${message(code: 'user.email.label')}" value="${profileCommand?.email}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'repeatEmail', 'error')}">
    <label class="control-label" for="repeatEmail">
        <strong><g:message code="user.repeatEmail.label" /></strong>
    </label>
    <div class="controls">
        <g:textField name="repeatEmail" placeholder="${message(code: 'user.repeatEmail.label')}" value="${profileCommand?.repeatEmail}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'password', 'error')}">
    <label class="control-label" for="password">
        <strong><g:message code="user.password.label" /></strong>
    </label>
    <div class="controls">
        <g:passwordField name="password" placeholder="${message(code: 'user.password.label')}" value="${profileCommand?.password}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'repeatPassword', 'error')}">
    <label class="control-label" for="repeatPassword">
        <strong><g:message code="user.repeatPassword.label" /></strong>
    </label>
    <div class="controls">
        <g:passwordField name="repeatPassword"
                         placeholder="${message(code: 'user.repeatPassword.label')}"
                         value="${profileCommand?.repeatPassword}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'sendMail', 'error')} ">
    <div class="controls">
        <label class="checkbox" for="sendMail">
            <g:checkBox name="sendMail" value="${profileCommand?.sendMail}" />
            <g:message code="user.sendMail.label"/>
        </label>
    </div>
</div>

<div class="control-group ${hasErrors(bean: profileCommand, field: 'oldPassword', 'error')} required">
    <label class="control-label" for="repeatPassword">
        <strong><g:message code="user.oldPassword.label" />
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:passwordField name="oldPassword"
                         placeholder="${message(code: 'user.oldPassword.label')}"
                         value="${profileCommand?.oldPassword}"/>
    </div>
</div>
