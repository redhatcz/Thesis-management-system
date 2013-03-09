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
    <g:if test="${!applicationInstanceList?.empty}">
    <table class="table">
        <thead>
        <tr>
            <g:sortableColumn property="id"
                              title="${message(code: 'id.label', default: 'Id')}"/>
            <g:sortableColumn property="applicant"
                              title="${message(code: 'application.applicant.label', default: 'Applicant')}"/>
            <g:sortableColumn property="topic"
                              title="${message(code: 'topic.label', default: 'Topic')}"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${applicationInstanceList}" status="i" var="applicationInstance">
            <tr>
                <td>
                    <g:link action="show" id="${applicationInstance.id}">${fieldValue(bean: applicationInstance, field: "id")}</g:link>

                </td>
                <td>
                    <g:link controller="user" action="show" id="${applicationInstance.applicantId}">${fieldValue(bean: applicationInstance.applicant, field: "fullName")}</g:link>
                </td>
                <td>
                    <g:link controller="topic" action="show" id="${applicationInstance.topicId}">${fieldValue(bean: applicationInstance.topic, field: "title")}</g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:if test="${Util.isPaginationVisible(applicationInstanceTotal, params.max)}">
        <g:paginate total="${applicationInstanceTotal}" class="pagination-centered"/>
    </g:if>
    </g:if>
    <g:else>No applications found.</g:else>
</div>

<div class="span4">
    <div class="panel right">
        <h4>Manage Applications</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-info" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link>
        </div>
    </div>
</div>
</body>
</html>
