<%@ page import="com.redhat.theses.AppStatus; com.redhat.theses.util.Util; com.redhat.theses.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="application.show.title" args="[applicationInstance.topic.title]"/></title>
</head>

<body>
<div class="span8 content">
    <h2 class="header">
        <g:message code="application.show.header" args="[applicationInstance.topic.title]"/>
    </h2>

    <dl class="dl-horizontal tms-dl">
        <dt>
            <g:message code="application.applicant.label"/>
        </dt>
        <dd>
            <g:link controller="user" action="show" id="${applicationInstance?.applicantId}">
                <g:fieldValue bean="${applicationInstance?.applicant}" field="fullName"/>
            </g:link>
        </dd>
        <dt>
            <g:message code="topic.label"/>
        </dt>
        <dd>
            <g:link controller="topic" action="show" id="${applicationInstance?.topicId}"
                    params="[headline: Util.hyphenize(applicationInstance?.topic?.title)]">
                <g:fieldValue bean="${applicationInstance?.topic}" field="title"/>
            </g:link>
        </dd>
        <g:if test="${applicationInstance?.thesis}">
            <dt>
                <g:message code="thesis.label"/>
            </dt>
            <dd>
                <g:link controller="thesis" action="show" id="${applicationInstance?.thesisId}"
                        params="[headline: Util.hyphenize(applicationInstance?.thesis?.title)]">
                    <g:fieldValue bean="${applicationInstance?.thesis}" field="title"/>
                </g:link>
            </dd>
        </g:if>
        <dt>
            <g:message code="application.type.label"/>
        </dt>
        <dd>
            <g:message code="application.type.${fieldValue(bean: applicationInstance, field: 'type')?.toString()?.toLowerCase()}.label"/>
        </dd>
        <dt>
            <g:message code="university.label"/>
        </dt>
        <dd>
            <g:fieldValue bean="${applicationInstance?.university}" field="name"/>
        </dd>
        <dt>
            <g:message code="application.date.label"/>
        </dt>
        <dd>
            <g:formatDate date="${applicationInstance?.dateCreated}" dateStyle="LONG" type="date"/>
        </dd>
        <dt>
            <g:message code="application.status.label" />
        </dt>
        <dd>
            <g:message code="application.status.${applicationInstance?.status?.toString()?.toLowerCase()}.label"/>
        </dd>
        <g:if test="${!applicationInstance?.note?.empty}">
            <dt>
                <g:message code="application.note.label"/>
            </dt>
            <dd>
                <g:fieldValue field="note" bean="${applicationInstance}"/>
            </dd>
        </g:if>
    </dl>
</div>
<sec:ifAnyGranted roles="ROLE_SUPERVISOR, ROLE_OWNER">
    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="application.management.label"/></h4>
            <div class="panel-content">
                <div class="panel-buttons">
                    <g:if test="${applicationInstance?.status != AppStatus.APPROVED}">
                        <g:link class="tms-link btn-link" action="approve" id="${applicationInstance?.id}">
                            <i class="icon-ok"></i>
                            <g:message code="application.approve.button"/>
                        </g:link>
                    </g:if>
                    <g:if test="${applicationInstance?.status != AppStatus.DECLINED}">
                        <g:link class="tms-link btn-link" action="decline" id="${applicationInstance?.id}"
                                onclick="return confirm('${message(code: 'default.decline.confirm.message')}');">
                            <i class="icon-ban-circle"></i>
                            <g:message code="application.decline.button"/>
                        </g:link>
                    </g:if>
                </div>
            </div>
        </div>
    </div>
</sec:ifAnyGranted>
</body>
</html>
