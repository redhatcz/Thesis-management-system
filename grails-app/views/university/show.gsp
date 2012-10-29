
<%@ page import="com.redhat.theses.University" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'university.label', default: 'University')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-university" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-university" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list university">
			
				<g:if test="${universityInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="university.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${universityInstance}" field="name"/></span>
					
				</li>
				</g:if>

                <g:if test="${!users.empty}">
                    <li class="fieldcontain">
                        <span id="users-label" class="property-label"><g:message code="university.users.label" default="Users" /></span>

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
					<g:hiddenField name="university.id" value="${universityInstance?.id}" />
					<g:link class="edit" action="edit" id="${universityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
