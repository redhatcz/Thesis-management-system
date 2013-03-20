<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="thesis.list.title" /></title>
</head>
<body>
    <div class="span8 content">
        <g:each var="thesis" in="${thesisInstanceList}">
        <div class="table-layout">
            <h4>
                <g:link action="show" id="${thesis.id}"
                        params="[headline: Util.hyphenize(thesis?.title)]">
                    <g:fieldValue bean="${thesis}" field="title"/>
                </g:link>
            </h4>
            <ul class="inline">
                <li><i class="icon-user"></i>
                    <g:link controller="user" action="show"
                            id="${thesis.assigneeId}">${thesis?.assignee?.fullName}</g:link>
                </li>
                <li><i class="icon-time"></i>
                    <g:formatDate date="${thesis?.dateCreated}"
                                  dateStyle="LONG"
                                  type="date" />
                </li>
                <li><i class="icon-user"></i>
                    <g:link controller="user" action="show" id="${thesis.supervisorId}">${thesis?.supervisor?.fullName}</g:link>
                </li>
            </ul>
        </div>
        </g:each>

        <g:if test="${Util.isPaginationVisible(thesisInstanceTotal, params.max)}">
            <g:paginate total="${thesisInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <sec:ifAnyGranted roles="ROLE_SUPERVISOR, ROLE_OWNER">
    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="thesis.list.manage.label"/></h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create"><g:message code="thesis.create.button"/></g:link>
            </div>
        </div>
    </div>
    </sec:ifAnyGranted>
</body>
</html>
