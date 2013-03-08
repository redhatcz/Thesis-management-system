<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8">
        <g:if test="${topicInstance?.title}">
        <h1 class="header"><g:fieldValue bean="${topicInstance}" field="title"/></h1>
        </g:if>

        <g:if test="${topicInstance?.description}">
        <markdown:renderHtml text="${topicInstance?.description}"/>
        </g:if>

        <g:set var="topicTags" value="${topicInstance?.tags}"/>
        <g:if test="${topicTags}">
        <p class="tag-list">
            <span class="entypo-tag big"></span><g:message code="topic.tags.label" default="tags" />:
            <g:each in="${topicTags}" var="t" status="i">
                <g:link controller="tag" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link><g:if test="${topicTags?.size() - 1 != i}">,</g:if>
            </g:each>
        </p>
        </g:if>

        <richg:comments comments="${comments}" article="${topicInstance}" commentsTotal="${commentsTotal}"/>
    </div>

    <div class="span4">
        <div class="panel right">
            <h4>Topic Information</h4>
            <div class="panel-content">
                <dl>
                <g:if test="${topicInstance?.owner}">
                    <dt>
                        <span class="entypo-user mini"></span><g:message code="topic.owner.label" default="owner" />
                    </dt>
                    <dd>
                        <g:link controller="user" action="show" id="${topicInstance?.owner?.id}">${topicInstance?.owner?.encodeAsHTML()}</g:link>
                    </dd>
                </g:if>

                <g:if test="${topicInstance?.dateCreated}">
                    <dt>
                        <span class="entypo-clock mini"></span><g:message code="topic.dateCreated.label" default="created" />
                    </dt>
                    <dd>
                        <g:formatDate date="${topicInstance?.dateCreated}" dateStyle="LONG" type="date" />
                    </dd>
                </g:if>

                <g:if test="${!supervisions.empty}">
                    <dt>
                        <span class="entypo-users mini"></span><g:message code="topic.supervisions.label" default="supervisions" />
                    </dt>
                    <g:each in="${supervisions}" status="i" var="supervision">
                    <dd>
                        <g:link controller="user" action="show" id="${supervision?.supervisor?.id}" title="${supervision?.university?.name}">${supervision?.supervisor?.encodeAsHTML()}</g:link>
                    </dd>
                    </g:each>
                </g:if>

                <g:if test="${!topicInstance?.types?.empty}">
                    <dt>
                        <span class="entypo-thesis mini"></span><g:message code="topic.types.label" default="types" />
                    </dt>
                    <dd>
                    <g:each in="${topicInstance?.types}" status="i" var="type">
                        <g:message code="topic.type.${type?.toString().toLowerCase()}.label"/><g:if test="${i != topicInstance?.types?.size() - 1}"><span>, </span></g:if>
                    </g:each>
                    </dd>
                </g:if>
                </dl>
            </div>

            <h4>Topic Management</h4>
            <div class="panel-content">

                <g:form style="display: inline;">
                    <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
                    <g:actionSubmit class="tms-btn tms-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:form>

                <g:if test="${!subscriber}">
                <g:form style="display: inline;" controller="subscription" action="subscribe">
                    <g:hiddenField name="articleId" value="${topicInstance?.id}"/>
                    <g:submitButton class="tms-btn tms-info" name="submit-subscription" value="Subscribe"/>
                </g:form>
                </g:if>

                <g:else>
                <g:form style="display: inline;" controller="subscription" action="unsubscribe">
                    <g:hiddenField name="articleId" value="${topicInstance?.id}"/>
                    <g:submitButton class="tms-btn tms-info" name="submit-unsubscription" value="Unsubscribe"/>
                </g:form>
                </g:else>

                <g:link class="tms-btn tms-success" controller="application" action="create" id="${topicInstance?.id}"><g:message code="default.button.apply.label" default="Apply" /></g:link>

                <g:link class="tms-btn tms-info" controller="thesis" action="create" id="${topicInstance?.id}"><g:message code="thesis.create.button" default="New Thesis" /></g:link>

                <g:link class="tms-btn tms-warning" action="edit" id="${topicInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>

                <g:link class="tms-btn tms-warning" controller="supervision" action="manage" id="${topicInstance?.id}"><g:message code="supervisions.edit" default="My Supervisions" /></g:link>

            </div>
        </div>
    </div>
</body>
</html>
