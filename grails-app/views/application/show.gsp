<%@ page import="com.redhat.theses.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="application.show.title" args="[applicationInstance?.id]"/></title>
</head>
<body>
<div class="span8 content">
    <h1 class="header">
        <g:fieldValue bean="${applicationInstance?.applicant}" field="fullName"/>  -
        <g:fieldValue bean="${applicationInstance?.topic}" field="title"/>
    </h1>

    <dl class="dl-horizontal">
        <dt><g:message code="application.applicant.label"/></dt>
        <dd><g:link controller="user" action="show" id="${applicationInstance?.applicantId}"><g:fieldValue bean="${applicationInstance?.applicant}" field="fullName"/></g:link></dd>

        <dt><g:message code="topic.label"/></dt>
        <dd><g:link controller="topic" action="show" id="${applicationInstance?.topicId}"><g:fieldValue bean="${applicationInstance?.topic}" field="title"/></g:link></dd>

        <dt><g:message code="university.label"/></dt>
        <dd><g:fieldValue bean="${applicationInstance?.university}" field="name"/></dd>

        <dt><g:message code="application.date.label"/></dt>
        <dd><g:formatDate date="${applicationInstance?.dateCreated}" dateStyle="LONG" type="date" /></dd>

        <dt><g:message code="application.approved.label" default="Approved"/></dt>
        <dd><g:formatBoolean boolean="${applicationInstance?.approved}"/></dd>

        <g:if test="${!applicationInstance?.note?.empty}">
            <dt><g:message code="application.note.label"/></dt>
            <dd><g:fieldValue field="note" bean="${applicationInstance}"/></dd>
        </g:if>
    </dl>
</div>
<div class="span4 sidebar">
    <div class="panel right">
        <h4><g:message code="application.management.label"/></h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-warning" action="approve" id="${applicationInstance?.id}"><g:message code="application.approve.button"/></g:link>
        </div>
    </div>
</div>
</body>
</html>
