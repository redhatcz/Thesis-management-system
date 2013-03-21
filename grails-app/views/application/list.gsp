<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Application" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="application.list.title"/></title>
</head>

<body>
<div class="span8 content">
    <h2 class="header"><g:message code="application.list.header"/></h2>
    <g:if test="${applicationInstanceList && !applicationInstanceList?.empty}">
    <div class="tms-table">
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
                        <g:link action="show" id="${applicationInstance?.id}">${fieldValue(bean: applicationInstance, field: "id")}</g:link>
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
    </div>
    <g:if test="${Util.isPaginationVisible(applicationInstanceTotal, params.max)}">
        <g:paginate total="${applicationInstanceTotal}" class="pagination-centered"/>
    </g:if>
    </g:if>
    <g:else><g:message code="application.no.applications.found"/></g:else>
</div>
</body>
</html>
