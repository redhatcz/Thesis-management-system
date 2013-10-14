<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title><g:message code="user.applications.title" args="[userInstance.fullName]"/></title>
</head>
<body>
    <content tag="active-button">applications</content>

    <content tag="main-box">
        <g:if test="${applicationList && !applicationList?.empty}">
            <div class="tms-table">
                <table class="table">
                    <thead>
                    <tr>
                        <g:sortableColumn property="topic"
                                          title="${message(code: 'topic.label')}"/>
                        <th><g:message code="application.status.label"/></th>
                        <th><g:message code="action.label"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${applicationList}" status="i" var="application">
                        <tr>
                            <td>
                                <g:link controller="topic" action="show" id="${application.topicId}"
                                        params="[headline: Util.hyphenize(application?.topic?.title)]">${fieldValue(bean: application.topic, field: "title")}</g:link>
                            </td>
                            <td>
                                <g:message code="application.status.${application?.status?.toString()?.toLowerCase()}.label"/>
                            </td>
                            <td>
                                <g:link controller="application" action="show" id="${application?.id}"><g:message code="action.show.label" /></g:link>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <g:if test="${Util.isPaginationVisible(applicationCount, params.max)}">
                <g:paginate total="${applicationCount}" class="pagination-centered"/>
            </g:if>
        </g:if>
        <g:else><p class="well well-large"><g:message code="application.no.applications.found"/></p></g:else>
    </content>
</body>
</html>
