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
    <h1 class="header"><g:fieldValue bean="${thesisInstance}" field="title"/></h1>
    <g:if test="${thesisInstance?.description}">
        <markdown:renderHtml text="${thesisInstance?.description}"/>
    </g:if>

    <g:if test="${thesisInstance?.thesisAbstract}">
        <h3>Abstract</h3>
        <g:fieldValue field="thesisAbstract" bean="${thesisInstance}"/>
    </g:if>

    <richg:comments comments="${comments}" article="${thesisInstance}" commentsTotal="${commentsTotal}"/>
</div>
<div class="span4">
    <div class="panel right">
        <h4>Thesis Information</h4>
        <div class="panel-content">
            <dl>
                <dt>
                    <span class="entypo-thesis mini"></span><g:message code="thesis.topic.label" default="topic"/>
                </dt>
                <dd>
                    <g:link action="show" controller="topic" id="${thesisInstance?.topicId}"><g:fieldValue field="topic" bean="${thesisInstance}"/></g:link>
                </dd>
                <dt>
                    <span class="entypo-user mini"></span><g:message code="thesis.assignee.label" default="assignee"/>
                </dt>
                <dd>
                    <g:link controller="user" action="show" id="${thesisInstance?.assigneeId}"><g:fieldValue field="assignee" bean="${thesisInstance}"/></g:link>
                </dd>
                <g:if test="${thesisInstance?.supervisor}">
                <dt>
                    <span class="entypo-user mini"></span><g:message code="thesis.supervisor.label" default="supervisor"/>
                </dt>
                <dd>
                    <g:link controller="user" action="show" id="${thesisInstance?.supervisorId}"><g:fieldValue field="fullName" bean="${thesisInstance?.supervisor}"/></g:link>
                </dd>
                </g:if>
                <dt>
                    <span class="entypo-clock mini"></span><g:message code="thesis.dateCreated.label" default="created"/>
                </dt>
                <dd>
                    <g:formatDate date="${thesisInstance?.dateCreated}" dateStyle="LONG" type="date" />
                </dd>
                <dt>
                    <span class="entypo-status mini"></span><g:message code="thesis.status.label" default="status"/>
                </dt>
                <dd>
                <g:if test="${thesisInstance?.status?.toString() != 'FINISHED'}">
                    <g:message code="thesis.status.${thesisInstance?.status?.toString()?.toLowerCase()}.label" />
                </g:if>
                <g:else>
                    This has been awarded the grade <strong>${thesisInstance?.grade}</strong>.
                </g:else>
                </dd>
            </dl>
        </div>

        <h4>Thesis Management</h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-warning" controller="thesis" action="edit" id="${thesisInstance?.id}"><g:message code="thesis.edit.button" default="Edit" /></g:link>
            <g:form style="display: inline;">
                <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
                <g:actionSubmit class="tms-btn tms-danger"
                                action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </g:form>

            <g:if test="${!subscriber}">
            <g:form style="display: inline;" controller="subscription" action="subscribe">
                <g:hiddenField name="articleId" value="${thesisInstance?.id}"/>
                <g:submitButton class="tms-btn tms-info" name="submit-subscription" value="Subscribe"/>
            </g:form>
            </g:if>

            <g:else>
            <g:form style="display: inline;" controller="subscription" action="unsubscribe">
                <g:hiddenField name="articleId" value="${thesisInstance?.id}"/>
                <g:submitButton class="tms-btn tms-info" name="submit-unsubscription" value="Unsubscribe"/>
            </g:form>
            </g:else>

        </div>
    </div>
</div>
</body>
</html>
