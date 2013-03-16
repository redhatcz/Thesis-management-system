<%@ page import="com.redhat.theses.util.Util; com.redhat.theses.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="user/showLayout"/>
    <title><g:message code="user.activity.title" args="[userInstance.fullName]"/></title>
</head>
<body>
    <content tag="active-button">activity</content>

    <content tag="main-box">
        <g:if test="${feedList && feedList.size() != 0}">
        <g:each in="${feedList}" var="feed">
        <div class="table-layout">
            <g:message code="${feed?.messageCode}" args="${feed?.args}"/>
        </div>
        </g:each>
        </g:if>

        <g:else>
        <div class="well well-large">
            <g:message code="user.no.activity.found"/>
        </div>
        </g:else>
    </content>
</body>
</html>
