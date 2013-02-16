<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="span8">
    <h1 class="header">
        <g:message code="default.list.label" args="[entityName]"/>
    </h1>
    <table class="table">
        <thead>
        <tr>
            <g:sortableColumn property="id" title="${message(code: 'thesis.id.label', default: 'Id')}"/>
            <g:sortableColumn property="topic" title="${message(code: 'thesis.topic.label', default: 'Thesis topic')}"/>
            <g:sortableColumn property="author" title="${message(code: 'thesis.assingee.label', default: 'Assignee')}"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${thesisInstanceList}" status="i" var="thesisInstance">
            <tr>
                <td>
                    <g:link action="show" id="${thesisInstance.id}">
                        <g:fieldValue field="id" bean="${thesisInstance}"/>
                    </g:link>
                </td>
                <td>
                    <g:fieldValue field="title" bean="${thesisInstance?.topic}"/>
                </td>
                <td>
                    <g:fieldValue field="fullName" bean="${thesisInstance?.assignee}"/>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <g:if test="${Util.isPaginationVisible(thesisInstanceTotal, params.max)}">
        <g:paginate total="${thesisInstanceTotal}" class="pagination-centered"/>
    </g:if>
</div>

<div class="span4">
    <div class="panel right">
        <h4>Manage theses</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-info" action="create">
                <g:message code="default.new.label" args="[entityName]"/>
            </g:link>
        </div>
    </div>
</div>
</body>
</html>
