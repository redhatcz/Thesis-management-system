<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Application" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'application.label', default: 'Application')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="span8">
    <h1 class="header"><g:message code="default.list.label" args="[entityName]"/></h1>
    <table class="table">
        <thead>
        <tr>
            <g:sortableColumn property="applicant"
                              title="${message(code: 'application.applicant', default: 'Applicant')}"/>
            <g:sortableColumn property="topic"
                              title="${message(code: 'topic.label', default: 'Topic')}"/>
            <g:sortableColumn property="supervisor"
                              title="${message(code: 'application.supervisor', default: 'Supervisor')}"/>
            <td>${message(code: 'actions', default: 'Actions')}</td>
        </tr>
        </thead>
        <tbody>
        <g:each in="${applicationInstanceList}" status="i" var="applicationInstance">
            <tr>
                <td>
                    <g:link action="show" id="${applicationInstance.id}">${fieldValue(bean: applicationInstance.applicant, field: "fullName")}</g:link>
                </td>
                <td>${fieldValue(bean: applicationInstance.topic, field: "title")}</td>
                <td>${fieldValue(bean: applicationInstance.supervisor, field: "fullName")}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:if test="${Util.isPaginationVisible(applicationInstanceTotal, params.max)}">
        <g:paginate total="${applicationInstanceTotal}" class="pagination-centered"/>
    </g:if>
</div>

<div class="span4">
    <div class="panel right">
        <h4>Manage applications</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-info" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link>
        </div>
    </div>
</div>
</body>
</html>
