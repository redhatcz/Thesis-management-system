<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="user.list.title" /></title>
</head>
<body>
    <div class="span8 content">
        <h2 class="header"><g:message code="user.list.header" /></h2>
        <div class="tms-table">
            <table class="table">
                <thead>
                    <tr>
                        <g:sortableColumn property="fullName"
                                          title="${message(code: 'user.fullName.label')}" />
                        <g:sortableColumn property="dateCreated"
                                          title="${message(code: 'user.dateCreated.label')}" />
                    </tr>
                </thead>
                <tbody>
                <g:each in="${userInstanceList}" status="i" var="userInstance">
                    <tr>
                        <td>
                            <g:link action="show" id="${userInstance.id}">
                                <g:fieldValue bean="${userInstance}" field="fullName"/>
                            </g:link>
                        </td>
                        <td>
                            <g:formatDate date="${userInstance?.dateCreated}"
                                          dateStyle="LONG" type="date" />
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        <g:if test="${Util.isPaginationVisible(userInstanceTotal, params.max)}">
            <g:paginate total="${userInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>

    <div class="span4 sidebar">
        <div class="panel right">
            <h4><g:message code="user.list.manage.label"/></h4>
            <div class="panel-content">
                <g:link class="tms-btn tms-info" action="create"><g:message code="user.create.button" /></g:link>
            </div>
        </div>
    </div>
</body>
</html>
