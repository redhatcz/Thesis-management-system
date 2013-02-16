<%@ page import="com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="profile">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div class="span4">
    <div class="avatar">
        %{--Profile picture will be here soon!--}%
    </div>

    <div class="panel left">
        <h4>User Information</h4>
        <dl class="panel-content">
            <g:if test="${userInstance?.email}">
                <dt><g:message code="user.email.label" default="Email" /></dt>
                <dd><g:fieldValue bean="${userInstance}" field="email"/></dd>
            </g:if>
            <g:if test="${userInstance?.fullName}">
                <dt><g:message code="user.fullName.label" default="Full Name" /></dt>
                <dd><g:fieldValue bean="${userInstance}" field="fullName"/></dd>
            </g:if>
            <g:if test="${userInstance?.accountExpired}">
                <dt><g:message code="user.accountExpired.label" default="Account Expired" /></dt>
                <dd><g:formatBoolean boolean="${userInstance?.accountExpired}" /></dd>
            </g:if>
            <g:if test="${userInstance?.accountLocked}">
                <dt><g:message code="user.accountLocked.label" default="Account Locked" /></dt>
                <dd><g:formatBoolean boolean="${userInstance?.accountLocked}" /></dd>
            </g:if>
            <g:if test="${userInstance?.enabled}">
                <dt><g:message code="user.enabled.label" default="Enabled" /></dt>
                <dd><g:formatBoolean boolean="${userInstance?.enabled}" /></dd>
            </g:if>
            <g:if test="${userInstance?.passwordExpired}">
                <dt><g:message code="user.passwordExpired.label" default="Password Expired" /></dt>
                <dd><g:formatBoolean boolean="${userInstance?.passwordExpired}" /></dd>
            </g:if>
        </dl>
    </div>
</div>

<div class="span8">
    <g:form>
        <div class="control-group">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#">Theses</a>
                </li>
                <li>
                    <a href="#">Activity</a>
                </li>
                <div class="controls pull-right">
                    <g:hiddenField name="user.id" value="${userInstance?.id}" />
                    <g:link class="tms-btn tms-warning" action="edit" id="${userInstance?.id}">
                        <g:message code="default.button.edit.label" default="Edit" />
                    </g:link>
                    <g:actionSubmit class="tms-btn tms-danger" action="delete"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </div>
            </ul>
        </div>
    </g:form>
    <g:if test="${topicInstanceList.isEmpty()}">
        <p class="center muted">No topics were found.</p>
    </g:if>
    <g:else>
        <g:each var="topic" in="${topicInstanceList}">
            <div class="table-layout">
                <h4>
                    <g:link controller="supervision" action="manage" id="${topic.id}">
                        <g:fieldValue bean="${topic}" field="title"/>
                    </g:link>
                </h4>
                <g:fieldValue bean="${topic}" field="lead"/>
            </div>
        </g:each>
    </g:else>
</div>
</body>
</html>
