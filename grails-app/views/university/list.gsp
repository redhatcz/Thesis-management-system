<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="university.list.title" /></title>
</head>
<body>
    <div class="span8 content">
        <h2 class="header"><g:message code="university.list.header" /></h2>
        <div class="tms-table">
            <table class="table">
                <thead>
                    <tr>
                        <g:sortableColumn property="name"
                                          title="${message(code: 'university.name.label')}" />
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${universityInstanceList}" status="i" var="universityInstance">
                    <tr>
                        <td>
                            <g:link action="show" id="${universityInstance.id}">${fieldValue(bean: universityInstance, field: "name")}</g:link>
                        </td>
                    </tr>
                    </g:each>
                </tbody>
            </table>
        </div>
        <g:if test="${Util.isPaginationVisible(universityInstanceTotal, params.max)}">
            <g:paginate total="${universityInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <div class="span4 sidebar">
            <div class="panel right">
                <h4><g:message code="university.list.manage.label"/></h4>
                <div class="panel-content">
                    <div class="panel-buttons">
                        <g:link class="tms-link btn-link" action="create">
                            <i class="icon-plus"></i>
                            <g:message code="university.create.button" />
                        </g:link>
                    </div>
                </div>
            </div>
        </div>
    </sec:ifAnyGranted>
</body>
</html>
