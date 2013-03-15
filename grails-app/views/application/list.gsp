<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Application" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="application.list.title"/></title>
</head>

<body>
<div class="span8 content">
    <h1 class="header"><g:message code="application.list.header"/></h1>
    <g:if test="${!applicationInstanceList?.empty}">
    <table class="table">
        <thead>
        <tr>
            <g:sortableColumn property="id"
                              title="${message(code: 'id.label')}"/>
            <g:sortableColumn property="applicant"
                              title="${message(code: 'application.applicant.label')}"/>
            <g:sortableColumn property="topic"
                              title="${message(code: 'topic.label')}"/>
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
                    <g:link controller="topic" action="show" id="${applicationInstance.topicId}"
                            params="[headline: Util.hyphenize(applicationInstance?.topic?.title)]">${fieldValue(bean: applicationInstance.topic, field: "title")}</g:link>
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

<div class="span4 sidebar">
    <div class="panel right">
        <h4>Manage Applications</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-info" action="create"><g:message code="application.create.button"/></g:link>
        </div>
    </div>
</div>
</body>
</html>
