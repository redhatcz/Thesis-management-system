<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<g:applyLayout name="profile">
    <html>
    <head>
        <title><g:layoutTitle default="Show user"/></title>
    </head>
    <body>
    <div class="span4">
        <div class="avatar">
            %{--Profile picture will be here soon!--}%
        </div>

        <div class="thesis">
            <h4>User Information</h4>
            <dl>
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
                <g:if test="${!memberships.empty}">
                    <dt><g:message code="user.memberships.label" default="Memberships" /></dt>
                    <g:each in="${memberships*.organization}" var="organization">
                        <dd><g:fieldValue bean="${organization}" field="name"/></dd>
                    </g:each>
                </g:if>
            </dl>
        </div>
    </div>

    <div class="span8">
        <div class="control-group">
            <ul class="nav nav-tabs">
                <li class="${pageProperty(name: 'page.active-button') == 'theses' ? 'active' : ''}">
                    <g:link action="show" id="${userInstance?.id}">Theses</g:link>
                </li>
                <li class="${pageProperty(name: 'page.active-button') == 'activity' ? 'active' : ''}">
                    <g:link action="activity" id="${userInstance?.id}">Activity</g:link>
                </li>
                <div class="controls pull-right">
                    <g:form controller="user" class="form-inline">
                        <g:link class="btn btn-warning" controller="user" action="edit" id="${userInstance?.id}">
                            <g:message code="default.button.edit.label" default="Edit" />
                        </g:link>
                        <g:hiddenField name="user.id" value="${userInstance?.id}" />
                        <g:actionSubmit class="btn btn-danger" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
            </ul>
        </div>
        <div>
            <g:pageProperty name="page.main-box"/>
        </div>
    </div>
    </body>
    </html>
</g:applyLayout>