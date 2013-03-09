<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8 content">
        <h1 class="header"><g:message code="default.list.label" args="[entityName]"/></h1>
        <table class="table">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="${message(code: 'thesis.title.label', default: 'Thesis title')}"/>
                <g:sortableColumn property="assignee" title="${message(code: 'thesis.assingee.label', default: 'Assignee')}"/>
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
            <h4>Manage Theses</h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link>
            </div>
        </div>
    </div>
</body>
</html>
