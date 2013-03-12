<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="thesis.list.title" /></title>
</head>
<body>
    <div class="span8 content">
        <h1 class="header"><g:message code="default.list.label" args="[entityName]"/></h1>
        <table class="table">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="${message(code: 'thesis.title.label')}"/>
                <g:sortableColumn property="assignee" title="${message(code: 'thesis.assignee.label')}"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${thesisInstanceList}" status="i" var="thesisInstance">
                <tr>
                    <td>
                        <g:link action="show" id="${thesisInstance.id}"><g:fieldValue field="title" bean="${thesisInstance}"/></g:link>
                    </td>
                    <td>
                        <g:link action="show" id="${thesisInstance.assigneeId}"><g:fieldValue field="fullName" bean="${thesisInstance?.assignee}"/></g:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <g:if test="${Util.isPaginationVisible(thesisInstanceTotal, params.max)}">
            <g:paginate total="${thesisInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="thesis.management.label"/></h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create"><g:message code="thesis.create.button"/></g:link>
            </div>
        </div>
    </div>
</body>
</html>
