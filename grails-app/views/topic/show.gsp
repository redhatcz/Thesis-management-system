<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8">
        <g:if test="${topicInstance?.title}">
            <h1 class="header">
                <g:fieldValue bean="${topicInstance}" field="title"/>
            </h1>
        </g:if>

        <g:if test="${topicInstance?.description}">
            <markdown:renderHtml text="${topicInstance?.description}"/>
        </g:if>
    </div>

        %{--<g:if test="${!supervisions.empty}">--}%
            %{--<li class="fieldcontain">--}%
                %{--<span id="supervisions-label" class="property-label"><g:message code="topic.supervisions.label" default="Supervisions" /></span>--}%
                %{--<g:each in="${supervisions}" status="i" var="supervision">--}%
                    %{--<span class="property-value" aria-labelledby="supervisions-label">--}%
                        %{--<g:fieldValue bean="${supervision.key}" field="name"/>--}%
                    %{--</span>--}%
                    %{--<g:each in="${supervision.value}" var="supervisor">--}%
                        %{--<span class="property-value" aria-labelledby="supervisions-label">--}%
                            %{--<g:link controller="user" action="show" id="${supervisor?.id}">${supervisor?.encodeAsHTML()}</g:link>--}%
                        %{--</span>--}%
                    %{--</g:each>--}%
                %{--</g:each>--}%
            %{--</li>--}%
        %{--</g:if>--}%

        %{--<g:if test="${topicInstance?.tags}">--}%
        %{--<li class="fieldcontain">--}%
            %{--<span id="tags-label" class="property-label"><g:message code="topic.tags.label" default="Tags" /></span>--}%
            %{--<g:each in="${topicInstance.tags}" var="t">--}%
            %{--<span class="property-value" aria-labelledby="tags-label"><g:link controller="tag" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>--}%
            %{--</g:each>--}%
        %{--</li>--}%
        %{--</g:if>--}%

    <div class="span4">
        <div class="thesis">
            <h4>Topic Information</h4>
            <dl class="dl-thesis">
                <g:if test="${topicInstance?.owner}">
                    <dt><g:message code="topic.owner.label" default="Owner" /></dt>
                    <dd><g:link controller="user" action="show" id="${topicInstance?.owner?.id}">
                        ${topicInstance?.owner?.encodeAsHTML()}
                    </g:link></dd>
                </g:if>
                <g:if test="${topicInstance?.dateCreated}">
                    <dt><g:message code="topic.dateCreated.label" default="Date Created" /></dt>
                    <dd><g:formatDate date="${topicInstance?.dateCreated}" dateStyle="LONG" type="date" /></dd>
                </g:if>
            </dl>
        </div>

        <div class="thesis">
            <h4>Topic Management</h4>
            <g:form>
                <fieldset>
                    <g:hiddenField name="topic.id" value="${topicInstance?.id}" />
                    <g:link class="btn btn-warning" action="edit" id="${topicInstance?.id}">
                        <g:message code="default.button.edit.label" default="Edit" />
                    </g:link>
                    <g:actionSubmit class="btn btn-danger" action="delete"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </div>

</body>
</html>
