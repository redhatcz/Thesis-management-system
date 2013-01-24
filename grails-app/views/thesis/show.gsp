<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="span8">
    <h1 class="header">
        Thesis: <g:fieldValue bean="${thesisInstance?.topic}" field="title"/>
    </h1>

    <g:if test="${thesisInstance?.thesisAbstract}">
        <markdown:renderHtml text="${thesisInstance?.thesisAbstract}"/>
    </g:if>

    <richg:comments comments="${comments}" article="${thesisInstance}" commentsTotal="${commentsTotal}"/>
</div>

<div class="span4">
    <div class="thesis">
        <h4>Thesis Information</h4>
        <dl class="dl-thesis">
            <dt><g:message code="thesis.assignee.label" default="Assignee"/></dt>
            <dd>
                <g:fieldValue field="assignee" bean="${thesisInstance}"/>
            </dd>
            <dt><g:message code="thesis.status.label" default="Status"/></dt>
            <dd>
                <g:message code="thesis.status.${thesisInstance.status.toString().toLowerCase()}.label"
                           default="${thesisInstance.status.toString()}"/>
            </dd>
            <g:if test="${thesisInstance?.grade}">
                <dt><g:message code="thesis.grade.label" default="Grade"/></dt>
                <dd><g:fieldValue bean="${thesisInstance}" field="grade"/></dd>
            </g:if>

        </dl>
    </div>

    <div class="thesis">
        <h4>Thesis Management</h4>
        <g:link class="btn btn-warning" controller="thesis" action="edit" id="${thesisInstance?.id}">
            <g:message code="thesis.edit.button" default="Edit" />
        </g:link>
    </div>
</div>

</body>
</html>
