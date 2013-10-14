<%@ page import="com.redhat.theses.AppStatus; com.redhat.theses.util.Util; com.redhat.theses.Application" %>
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
                <th><g:message code="application.status.label"/></th>
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
    <g:if test="${Util.isPaginationVisible(applicationInstanceTotal, params.max)}">
        <g:paginate total="${applicationInstanceTotal}" class="pagination-centered"
                    params="${Util.formatParams(request)}"/>
    </g:if>
    </g:if>
    <g:else>
        <p class="center muted"><g:message code="application.no.applications.found"/></p>
    </g:else>
</div>

<div class="span4 sidebar">
    <div class="panel right">
        <h4>
            <div class="small-msg pull-right">
                <i class="icon-info-sign icon-large"
                   title="${message(code:'info.filter.application')}"></i>
            </div>
            <g:message code="filters.label"/>
        </h4>
        <div class="panel-content">
            <g:form method="get" class="filter">
                <g:hiddenField name="filtering" value="true"/>
                <g:textField value="${params?.filter?.applicant?.fullName}" class="wide"
                             name="filter.applicant.fullName" placeholder="${message(code: 'application.applicant.label')}"/>
                <g:hiddenField name="type.applicant.fullName" value="ilike"/>
                <g:textField value="${params?.filter?.topic?.title}" class="wide"
                             name="filter.topic.title" placeholder="${message(code: 'topic.label')}"/>
                <g:hiddenField name="type.topic.title" value="ilike"/>
                <g:select name="filter.status" from="${AppStatus.values()}"
                          noSelection="['':message(code:'application.status.select.label')]"
                          optionValue="${{g.message(code:"application.status.${it?.toString()?.toLowerCase()}.label")}}"
                          value="${params?.filter?.status}"/>
                <g:submitButton class="tms-btn pull-right" name="filter-button"
                                value="${message(code: 'filter.button')}"/>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
