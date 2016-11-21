<%@ page import="com.redhat.theses.AppStatus; com.redhat.theses.util.Util; com.redhat.theses.auth.User"%>

<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="user/listLayout" />
<title><g:message code="request.list.label" /></title>
</head>
<body>
    <content tag="active-button">roleRequest</content>

    <content tag="main-box">
        <h2 class="header"><g:message code="request.list.label"/></h2>
        <g:if test="${requestInstanceList && !requestInstanceList?.empty}">
        <div class="tms-table">
            <table class="table">
                <thead>
                <tr>
                    <g:sortableColumn property="applicant"
                                      title="${message(code: 'application.applicant.label')}"/>
                    <th><g:message code="application.status.label"/></th>
                    <th><g:message code="action.label"/></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${requestInstanceList}" status="i" var="request">
                    <tr>
                        <td>
                            <g:link controller="user" action="show" id="${request.applicantId}">${fieldValue(bean: request.applicant, field: "fullName")}</g:link>
                        </td>
                        <td>
                            <g:message code="application.status.${request?.status?.toString()?.toLowerCase()}.label"/>
                        </td>
                        <td>
                            <g:link controller="roleRequest" action="show" id="${request?.id}"><g:message code="action.show.label" /></g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
        <g:if test="${Util.isPaginationVisible(requestInstanceTotal, params.max)}">
            <g:paginate total="${requestInstanceTotal}" class="pagination-centered"
                        params="${Util.formatParams(request)}"/>
        </g:if>
        </g:if>
        <g:else>
            <p class="muted"><g:message code="request.no.requests.found"/></p>
        </g:else>
    </content>

    <content tag="sidebar">
        <div class="panel right">
            <h4>
                <div class="small-msg pull-right">
                    <i class="icon-info-sign icon-large"
                       title="${message(code:'info.filter.request')}"></i>
                </div>
                <g:message code="filters.label"/>
            </h4>
            <div class="panel-content">
                <g:form method="get" class="filter">
                    <g:hiddenField name="filtering" value="true"/>
                    <g:textField value="${params?.filter?.applicant?.fullName}" class="wide"
                                 name="filter.applicant.fullName" placeholder="${message(code: 'application.applicant.label')}"/>
                    <g:select name="filter.status" from="${AppStatus.values()}"
                              noSelection="['':message(code:'application.status.select.label')]"
                              optionValue="${{g.message(code:"application.status.${it?.toString()?.toLowerCase()}.label")}}"
                              value="${params?.filter?.status}"/>
                    <g:submitButton class="tms-btn pull-right" name="filter-button"
                                    value="${message(code: 'filter.button')}"/>
                </g:form>
            </div>
        </div>
    </content>
</body>
</html>