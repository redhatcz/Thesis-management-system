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
                <g:sortableColumn property="topic"
                                  title="${message(code: 'topic.label')}"/>
                <g:sortableColumn property="applicant"
                                  title="${message(code: 'application.applicant.label')}"/>
                <th><g:message code="application.approved.label"/></th>
                <th><g:message code="action.label"/></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${applicationInstanceList}" status="i" var="application">
                <tr>
                    <td>
                        <g:link controller="topic" action="show" id="${application.topicId}"
                                params="[headline: Util.hyphenize(application?.topic?.title)]">${fieldValue(bean: application.topic, field: "title")}</g:link>
                    </td>
                    <td>
                        <g:link controller="user" action="show" id="${application.applicantId}">${fieldValue(bean: application.applicant, field: "fullName")}</g:link>
                    </td>
                    <td>
                        <g:formatBoolean boolean="${application?.approved}"/>
                    </td>
                    <td>
                        <g:link controller="application" action="show" id="${application?.id}"><g:message code="action.show.label" /></g:link>
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
