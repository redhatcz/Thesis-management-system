<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="home.title"/></title>
</head>
<body>
    <div class="span12 content">

        <g:if test="${config?.announcement}">
            <div class="alert alert-error">
                <markdown:renderHtml text="${config?.announcement}" />
            </div>
        </g:if>
		<markdown:renderHtml text="${upper}" />
		<g:form method="get" class="filter" url="[action:'list',controller:'topic']">
		    <g:hiddenField name="filtering" value="true"/>
			<g:textField name="filter.title-description-lead-secondaryDescription" class="homeSearch" placeholder="${message(code: 'topic.filter.title')}" />
			<g:submitButton class="tms-btn pull-right homeSubmit" name="filter-button"
							value="${message(code: 'home.search.submit')}" />
		</g:form>
		<hr />
		<markdown:renderHtml text="${lower}" />
    </div>
</body>
</html>
