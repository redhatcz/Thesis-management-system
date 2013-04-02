<%@ page import="com.redhat.theses.Grade; com.redhat.theses.Status; com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="thesis.list.title" /></title>
</head>
<body>
    <div class="span8 content">
        <g:if test="${thesisInstanceList && thesisInstanceList.size() != null}">
            <g:each var="thesis" in="${thesisInstanceList}">
            <div class="table-layout">
                <h4>
                    <g:link action="show" id="${thesis.id}"
                            params="[headline: Util.hyphenize(thesis?.title)]">
                        <g:fieldValue bean="${thesis}" field="title"/>
                    </g:link>
                </h4>
                <ul class="inline">
                    <li>
                        <i class="icon-user" title="${message(code:'thesis.assignee.label')}"></i>
                        <g:link controller="user" action="show"
                                id="${thesis.assigneeId}">${thesis?.assignee?.fullName}</g:link>
                    </li>
                    <li>
                        <i class="icon-time" title="${message(code:'thesis.dateCreated.label')}"></i>
                        <g:formatDate date="${thesis?.dateCreated}"
                                      dateStyle="LONG"
                                      type="date" />
                    </li>
                    <li>
                        <i class="icon-comment" title="${message(code:'comment.title')}"></i>
                        <g:link action="show" id="${thesis?.id}" fragment="comments">${commentCounts[thesis] ?: 0} comments</g:link>
                    </li>
                </ul>
            </div>
            </g:each>
        </g:if>
        <g:else>
            <p class="center muted"><g:message code="thesis.no.theses.found"/></p>
        </g:else>

        <g:if test="${Util.isPaginationVisible(thesisInstanceTotal, params.max)}">
            <g:paginate total="${thesisInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <sec:ifAnyGranted roles="ROLE_SUPERVISOR, ROLE_OWNER">
                <h4><g:message code="thesis.list.manage.label"/></h4>
                <div class="panel-content">
                    <div class="panel-buttons">
                        <g:link class="tms-link btn-link" action="create">
                            <i class="icon-plus"></i>
                            <g:message code="thesis.create.button"/>
                        </g:link>
                    </div>
                </div>
            </sec:ifAnyGranted>

            <h4><g:message code="filters.label"/></h4>
            <div class="panel-content">
                <g:form method="get" class="filter">
                    <g:hiddenField name="filtering" value="true"/>
                    <g:hiddenField name="filter.tags.id" value="${params?.filter?.tags?.id}"/>
                    <g:textField value="${params?.filter?.title}" class="wide"
                                 name="filter.title" placeholder="${message(code: 'thesis.title.label')}"/>
                    <g:textField value="${params?.filter?.supervisor?.fullName}" class="wide"
                                 name="filter.supervisor.fullName" placeholder="${message(code: 'thesis.supervisor.label')}"/>
                    <g:textField value="${params?.filter?.assignee?.fullName}" class="wide"
                                 name="filter.assignee.fullName" placeholder="${message(code: 'thesis.assignee.label')}"/>
                    <g:select name="filter.status" from="${Status.values()}"
                              noSelection="['':message(code:'thesis.status.select.label')]"
                              optionValue="${{g.message(code:"thesis.status.${it?.toString()?.toLowerCase()}.label")}}"
                              value="${params?.filter?.status}"
                              class="many-to-one"/>
                    <g:select name="filter.grade" from="${Grade.values()}"
                              noSelection="['':message(code:'thesis.grade.select.label')]"
                              value="${params?.filter?.grade}"
                              class="many-to-one"/>
                    <g:submitButton class="tms-btn pull-right" name="filter-button"
                                    value="${message(code: 'filter.button')}"/>
                </g:form>
            </div>

            <h4><g:message code="topic.tags.label"/></h4>
            <div class="panel-content">
                <g:render template="/shared/tags/tagList" />
            </div>
        </div>
    </div>
</body>
</html>
