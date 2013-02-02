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
            <dt><g:message code="thesis.topic.label" default="Topic"/></dt>
            <dd>
                <g:link action="show" controller="topic" id="${thesisInstance?.topic?.id}">
                    <g:fieldValue field="topic" bean="${thesisInstance}"/>
                </g:link>
            </dd>
            <dt><g:message code="thesis.assignee.label" default="Assignee"/></dt>
            <dd>
                <g:fieldValue field="assignee" bean="${thesisInstance}"/>
            </dd>
            <dt><g:message code="thesis.sMembership.organization.label" default="University"/></dt>
            <dd>
                <g:fieldValue field="name" bean="${thesisInstance?.sMembership?.organization}"/>
            </dd>
            <dt><g:message code="thesis.sMembership.user.label" default="Supervisor"/></dt>
            <dd>
                <g:fieldValue field="fullName" bean="${thesisInstance?.sMembership?.user}"/>
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
        <g:form style="display: inline;">
            <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
            <g:actionSubmit class="btn btn-danger" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
        <g:if test="${!subscriber}">
            <g:form style="display: inline;" controller="subscription" action="subscribe">
                <g:hiddenField name="articleId" value="${thesisInstance?.id}"/>
                <g:submitButton class="btn btn-info" name="submit-subscription" value="Subscribe"/>
            </g:form>
        </g:if>
        <g:else>
            <g:form style="display: inline;" controller="subscription" action="unsubscribe">
                <g:hiddenField name="articleId" value="${thesisInstance?.id}"/>
                <g:submitButton class="btn btn-info" name="submit-unsubscription" value="Unsubscribe"/>
            </g:form>
        </g:else>
    </div>
</div>

</body>
</html>
