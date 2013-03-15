<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <title><g:message code="thesis.show.title"/></title>
</head>
<body>
<div class="span8 content">
    <h1 class="header"><g:fieldValue bean="${thesisInstance}" field="title"/></h1>
    <g:if test="${thesisInstance?.description}">
        <markdown:renderHtml text="${thesisInstance?.description}"/>
    </g:if>

    <g:if test="${thesisInstance?.thesisAbstract}">
        <h3><g:message code="thesis.thesisAbstract.label"/></h3>
        <g:fieldValue field="thesisAbstract" bean="${thesisInstance}"/>
    </g:if>

    <richg:comments comments="${comments}" article="${thesisInstance}" commentsTotal="${commentsTotal}"/>
</div>
<div class="span4 sidebar">
    <div class="panel right">
        <h4><g:message code="thesis.information.label"/></h4>
        <div class="panel-content">
            <dl>
                <dt>
                    <i class="icon-book"></i>
                    ${message(code: 'topic.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:link action="show" controller="topic" id="${thesisInstance?.topicId}"
                            params="[headline: Util.hyphenize(thesisInstance?.topic?.title)]"><g:fieldValue field="topic" bean="${thesisInstance}"/></g:link>
                </dd>
                <dt>
                    <i class="icon-user"></i>
                    ${message(code: 'thesis.assignee.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:link controller="user" action="show" id="${thesisInstance?.assigneeId}"><g:fieldValue field="assignee" bean="${thesisInstance}"/></g:link>
                </dd>
                <g:if test="${thesisInstance?.supervisor}">
                <dt>
                    <i class="icon-user mini"></i>
                    ${message(code: 'thesis.supervisor.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:link controller="user" action="show" id="${thesisInstance?.supervisorId}"><g:fieldValue field="fullName" bean="${thesisInstance?.supervisor}"/></g:link>
                </dd>
                </g:if>
                <dt>
                    <i class="icon-time"></i>
                    ${message(code: 'thesis.dateCreated.label').toString().toLowerCase()}
                </dt>
                <dd>
                    <g:formatDate date="${thesisInstance?.dateCreated}" dateStyle="LONG" type="date" />
                </dd>
                <dt>
                    <i class="icon-question-sign"></i>
                    ${message(code: 'thesis.status.label').toString().toLowerCase()}
                </dt>
                <dd>
                <g:if test="${thesisInstance?.status?.toString() != 'FINISHED'}">
                    <g:message code="thesis.status.${thesisInstance?.status?.toString()?.toLowerCase()}.label" />
                </g:if>
                <g:else>
                    <g:message code="thesis.status.awarded.grade" args="[thesisInstance?.grade]"/>
                </g:else>
                </dd>
            </dl>
        </div>

        <h4><g:message code="thesis.management.label"/></h4>
        <div class="panel-content">
            <g:link class="tms-btn tms-warning" controller="thesis" action="edit" id="${thesisInstance?.id}"><g:message code="default.edit.button"/></g:link>
            <g:form style="display: inline;">
                <g:hiddenField name="thesis.id" value="${thesisInstance?.id}" />
                <g:actionSubmit class="tms-btn tms-danger"
                                action="delete"
                                value="${message(code: 'default.delete.button')}"
                                onclick="return confirm('${message(code: 'default.delete.confirm.message')}');" />
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
