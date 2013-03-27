<%@ page import="com.redhat.theses.auth.Role; com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="user.list.title" /></title>
</head>
<body>
    <div class="span12 content">
        <h2 class="header"><g:message code="user.list.header" /></h2>
        <g:if test="${userInstanceList && userInstanceList.size() != 0}">
        <table class="table table-users">
            <tbody>
            <g:each in="${(0..(Math.ceil(userInstanceList?.size() / 4) - 1))}" var="i">
                <tr>
                    <g:each in="${((4*i)..(4*i + 3))}" var="j">
                    <td>
                        <g:if test="${j < userInstanceList.size()}">
                        <div class="user-info">
                            <richg:avatar user="${userInstanceList?.get(j)}" small="true"
                                          class="img-polaroid avatar-mini" height="36" width="36"/>
                            <div class="user-text">
                                <h6>
                                    <g:link action="show" id="${userInstanceList?.get(j)?.id}">
                                        <g:fieldValue bean="${userInstanceList?.get(j)}" field="fullName"/>
                                    </g:link>
                                </h6>
                                <i class="icon-group"></i>
                                <g:message code="role.${Role.getHighest(userInstanceList?.get(j).roles).toString().toLowerCase()}.label"/>
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
                            <g:message code="user.create.button" />
                        </g:link>
                    </td>
                </tr>
                </sec:ifAnyGranted>
            </tbody>
        </table>
        </g:if>
        <g:else>
            <p class="center muted"><g:message code="user.no.users.found"/></p>
        </g:else>

        <g:if test="${Util.isPaginationVisible(userInstanceTotal, params.max)}">
            <g:paginate total="${userInstanceTotal}" class="pagination-centered"/>
        </g:if>
    </div>
</body>
</html>
