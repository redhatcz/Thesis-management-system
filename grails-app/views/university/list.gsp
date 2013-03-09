<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'university.label', default: 'University')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8 content">
        <h1 class="header">
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <table class="table">
            <thead>
                <tr>
                    <g:sortableColumn property="name"
                                      title="${message(code: 'university.name.label', default: 'Name')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${universityInstanceList}" status="i" var="universityInstance">
                <tr>
                    <td>
                        <g:link action="show" id="${universityInstance.id}">${fieldValue(bean: universityInstance, field: "name")}</g:link>
                    </td>
                </tr>
                </g:each>
            </tbody>
        </table>
        <g:if test="${Util.isPaginationVisible(universityInstanceTotal, params.max)}">
            <g:paginate total="${universityInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4>Manage Universities</h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
            </div>
        </div>
    </div>
</body>
</html>
