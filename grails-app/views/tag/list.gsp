<%@ page import="com.redhat.theses.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div class="span8 content">
        <h1 class="header"><g:message code="default.list.label" args="[entityName]" /></h1>
        <table class="table">
            <thead>
                <tr>
                    <g:sortableColumn property="title" title="${message(code: 'tag.title.label', default: 'Title')}" />
                    <th>
                        <g:message code="tag.parent.label" default="Parent" />
                    </th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${tagInstanceList}" status="i" var="tagInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>
                        <g:link action="show" id="${tagInstance.id}">${fieldValue(bean: tagInstance, field: "title")}</g:link>
                    </td>
                    <td>
                        ${fieldValue(bean: tagInstance, field: "parent")}
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4>Manage Tags</h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create">
                    <g:message code="default.new.label" args="[entityName]" />
                </g:link>
            </div>
        </div>
    </div>
</body>
</html>
