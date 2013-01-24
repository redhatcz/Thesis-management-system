<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.Organization" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'thesis.label', default: 'Thesis')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="span8">
    <h1 class="header">
        <g:message code="default.list.label" args="[entityName]"/>
    </h1>
    <table class="table">
        <thead>
        <tr>
            <g:sortableColumn property="topic" title="${message(code: 'topic.label', default: 'Topic')}"/>
            <g:sortableColumn property="author" title="${message(code: 'thesis.author.label', default: 'Author')}"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${thesisInstanceList}" status="i" var="thesisInstance">
            <tr>
                <td>
                    <g:link action="show" id="${thesisInstance.id}">
                        ${fieldValue(bean: thesisInstance.topic, field: "title")}
                    </g:link>
                </td>
                <td>
                    ${fieldValue(bean: thesisInstance.assignee, field: "fullName")}
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <g:if test="${Util.isPaginationVisible(thesisInstanceTotal, params.max)}">
        <g:paginate total="${thesisInstanceTotal}" class="pagination-centered"/>
    </g:if>
</div>

<div class="span4">
    <div class="thesis">
        <h4>Manage theses</h4>
        <g:link class="btn btn-info" action="create">
            <g:message code="default.new.label" args="[entityName]"/>
        </g:link>
    </div>
</div>
</body>
</html>
