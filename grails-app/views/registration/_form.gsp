<%@ page import="com.redhat.theses.auth.User" %>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'fullName', 'error')} required">
    <label class="control-label" for="fullName">
        <strong><g:message code="user.fullName.label"/>
            <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="fullName" placeholder="${message(code: 'user.fullName.label')}" value="${registrationCommand?.fullName}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'email', 'error')} required">
    <label class="control-label" for="email">
        <strong><g:message code="user.email.label"/>
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="email" placeholder="${message(code: 'user.email.label')}" value="${registrationCommand?.email}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'repeatEmail', 'error')} required">
    <label class="control-label" for="repeatEmail">
        <strong><g:message code="user.repeatEmail.label"/>
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:textField name="repeatEmail"
                     placeholder="${message(code: 'user.repeatEmail.label')}"
                     value="${registrationCommand?.repeatEmail}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'password', 'error')} required">
    <label class="control-label" for="password">
        <strong><g:message code="user.password.label"/>
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:passwordField name="password"
                         placeholder="${message(code: 'user.password.label')}"
                         value="${registrationCommand?.password}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'repeatPassword', 'error')} required">
    <label class="control-label" for="repeatPassword">
        <strong><g:message code="user.repeatPassword.label"/>
        <span class="required-indicator">*</span></strong>
    </label>
    <div class="controls">
        <g:passwordField name="repeatPassword"
                         placeholder="${message(code: 'user.repeatPassword.label')}"
                         value="${registrationCommand?.repeatPassword}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: registrationCommand, field: 'repeatPassword', 'error')} required">
    <div class="controls">
        <label class="checkbox" for="termsOfUse">
            <g:checkBox name="termsOfUse"
                        value="${registrationCommand?.termsOfUse}"/> <span class="required-indicator">*</span>
            <g:set var="touLink" value="${link(controller: 'termsOfUse', action: 'index',
                        message(code: 'registration.termsOfUse'), target: '_blank')}" />
            <g:message code="registration.termsOfUse.agreement.label" args="[touLink]"/>
        </label>
    </div>
</div>
