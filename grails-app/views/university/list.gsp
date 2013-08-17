<%@ page import="com.redhat.theses.auth.Role; com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="university.list.title" /></title>
</head>
<body>
<div class="span12 content">
    <h2 class="header"><g:message code="university.list.header" /></h2>
    <g:if test="${universityInstanceList && universityInstanceList.size() != 0}">
    <table class="table table-users">
        <tbody>
        <g:each in="${(0..(Math.ceil(universityInstanceList?.size() / 2) - 1))}" var="i">
            <tr>
                <g:each in="${((2*i)..(2*i + 1))}" var="j">
                    <td>
                        <g:if test="${j < universityInstanceList.size()}">
                        <div class="user-info">
                            <img class="img-polaroid avatar-mini" height="36" width="36"
                                 src="${resource(dir: 'images', file: 'avatar-university.png')}"/>
                            <div class="user-text">
                                <h6>
                                    <g:link action="show" id="${universityInstanceList?.get(j)?.id}">
                                        <g:fieldValue bean="${universityInstanceList?.get(j)}" field="name"/>
                                    </g:link>
                                </h6>
                                <g:fieldValue bean="${universityInstanceList?.get(j)}" field="acronym"/>
                            </div>
                        </div>
                        </g:if>
                    </td>
                </g:each>
            </tr>
        </g:each>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <tr>
                <td>
                    <g:link class="tms-create" action="create">
                        <i class="icon-plus-sign"></i>
                        <g:message code="university.create.button" />
                    </g:link>
                </td>
            </tr>
        </sec:ifAnyGranted>
        </tbody>
    </table>
    </g:if>
    <g:else>
        <p class="center muted"><g:message code="university.no.universities.found"/></p>
    </g:else>

    <g:if test="${Util.isPaginationVisible(universityInstanceTotal, params.max)}">
        <g:paginate total="${universityInstanceTotal}" class="pagination-centered"/>
    </g:if>
</div>
</body>
</html>
