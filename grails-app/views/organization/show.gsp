
<%@ page import="com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="profile">
    <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span4">
        <div class="avatar">
            %{--Organization logo will be here soon!--}%
        </div>

        <div class="thesis">
            <h4>Organization Information</h4>
            <dl>
                <g:if test="${organizationInstance?.name}">
                    <dt><g:message code="organization.name.label" default="Name" /></dt>
                    <dd><g:fieldValue bean="${organizationInstance}" field="name"/></dd>
                </g:if>
                <dt><g:message code="organization.type.label" default="Type" /></dt>
                <dd><g:message code="organization.type.value" default="${organizationInstance?.class?.simpleName}"/></dd>
                <g:if test="${!users.empty}">
                    <dt><g:message code="organization.users.label" default="Users" /></dt>
                    <g:each in="${users}" var="user">
                        <dd><g:fieldValue bean="${user}" field="fullName"/></dd>
                    </g:each>
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
                        <g:hiddenField name="organization.id" value="${organizationInstance?.id}" />
                        <g:link class="btn btn-warning" action="edit" id="${organizationInstance?.id}">
                            <g:message code="default.button.edit.label" default="Edit" />
                        </g:link>
                        <g:actionSubmit class="btn btn-danger" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </div>
                </ul>
            </div>
        </g:form>
    </div>
</body>
</html>
