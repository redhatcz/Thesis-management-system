
<%@ page import="com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-organization" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-organization" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list organization">
			
				<g:if test="${organizationInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="organization.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${organizationInstance}" field="name"/></span>
					
				</li>
				</g:if>

                <li class="fieldcontain">
                    <span id="type-label" class="property-label"><g:message code="organization.type.label" default="Type of organization" /></span>

                    <span class="property-value" aria-labelledby="name-label">
                        <g:message code="organization.type.value" default="${organizationInstance?.class?.simpleName}"/>
                    </span>

                </li>

                <g:if test="${!users.empty}">
                    <li class="fieldcontain">
                        <span id="users-label" class="property-label"><g:message code="organization.users.label" default="Users" /></span>

                        <div>
                            <g:each in="${users}" var="user">
                                <span class="property-value" aria-labelledby="users-label"><g:fieldValue bean="${user}" field="fullName"/></span>
                            </g:each>
                        </div>

                    </li>
                </g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="organization.id" value="${organizationInstance?.id}" />
					<g:link class="edit" action="edit" id="${organizationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
