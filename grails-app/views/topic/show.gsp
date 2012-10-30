
<%@ page import="com.redhat.theses.Topic" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-topic" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-topic" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list topic">

                <g:if test="${topicInstance?.title}">
                    <li class="fieldcontain">
                        <span id="title-label" class="property-label"><g:message code="topic.title.label" default="Title" /></span>

                        <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${topicInstance}" field="title"/></span>

                    </li>
                </g:if>

                <g:if test="${topicInstance?.owner}">
                    <li class="fieldcontain">
                        <span id="owner-label" class="property-label"><g:message code="topic.owner.label" default="Owner" /></span>
                        <span class="property-value" aria-labelledby="owner-label"><g:link controller="user" action="show" id="${topicInstance?.owner?.id}">${topicInstance?.owner?.encodeAsHTML()}</g:link></span>
                    </li>
                </g:if>

				<g:if test="${topicInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="topic.dateCreated.label" default="Date Created" /></span>
					<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${topicInstance?.dateCreated}" /></span>
				</li>
				</g:if>

                <g:if test="${!supervisions.empty}">
                    <li class="fieldcontain">
                        <span id="supervisions-label" class="property-label"><g:message code="topic.supervisions.label" default="Supervisions" /></span>
                            <g:each in="${supervisions}" status="i" var="supervision">
                                <span class="property-value" aria-labelledby="supervisions-label">
                                    <g:fieldValue bean="${supervision.key}" field="name"/>
                                </span>
                                <g:each in="${supervision.value}" var="supervisor">
                                    <span class="property-value" aria-labelledby="supervisions-label">
                                        <g:link controller="user" action="show" id="${supervisor?.id}">${supervisor?.encodeAsHTML()}</g:link>
                                    </span>
                                </g:each>
                            </g:each>
                    </li>
                </g:if>

				<g:if test="${topicInstance?.tags}">
				<li class="fieldcontain">
					<span id="tags-label" class="property-label"><g:message code="topic.tags.label" default="Tags" /></span>
					<g:each in="${topicInstance.tags}" var="t">
					<span class="property-value" aria-labelledby="tags-label"><g:link controller="tag" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
					</g:each>
				</li>
				</g:if>


                <g:if test="${topicInstance?.description}">
                    <li class="fieldcontain">
                        <span id="description-label" class="property-label"><g:message code="topic.description.label" default="Description" /></span>
                        <span class="property-value" aria-labelledby="description-label">
                            <markdown:renderHtml text="${topicInstance?.description}"/>
                    </li>
                </g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${topicInstance?.id}" />
					<g:link class="edit" action="edit" id="${topicInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
