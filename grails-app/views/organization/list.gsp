<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8">
        <h1 class="header">
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <table class="table">
            <thead>
                <tr>
                    <g:sortableColumn property="name" title="${message(code: 'organization.name.label', default: 'Name')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${organizationInstanceList}" status="i" var="organizationInstance">
                    <tr>
                        <td><g:link action="show" id="${organizationInstance.id}">
                            ${fieldValue(bean: organizationInstance, field: "name")}
                        </g:link></td>
                    </tr>
                </g:each>
            </tbody>
        </table>

        <g:if test="${Util.isPaginationVisible(organizationInstanceTotal, params.max)}">
            <g:paginate total="${organizationInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <div class="span4">
        <div class="panel right">
            <h4>Manage Organizations</h4>
            <div class="panel-content">
                <g:link class="btn btn-info" action="create">
                    <g:message code="default.new.label" args="[entityName]" />
                </g:link>
            </div>
        </div>
    </div>
</body>
</html>
