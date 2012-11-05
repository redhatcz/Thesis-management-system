
<%@ page import="com.redhat.theses.Tag" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tag" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tag" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<ol class="property-list tag">
			
				<g:if test="${tagInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="tag.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${tagInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tagInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="tag.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="tag" action="show" id="${tagInstance?.parent?.id}">${tagInstance?.parent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${tagInstance?.subTags}">
				<li class="fieldcontain">
					<span id="subTags-label" class="property-label"><g:message code="tag.subTags.label" default="Sub Tags" /></span>
					
						<g:each in="${tagInstance.subTags}" var="s">
						<span class="property-value" aria-labelledby="subTags-label"><g:link controller="tag" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="tag.id" value="${tagInstance?.id}" />
					<g:link class="edit" action="edit" id="${tagInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
