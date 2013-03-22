<%@ page import="com.redhat.theses.util.Util" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="notification.list.title" /></title>
</head>
<body>
<div class="span8 content">
    <h2 class="header"><g:message code="notification.list.header" /></h2>
    <div class="notification-list">
        <g:render template="/taglib/notification/notificationList" model="[notifications: notificationList]"/>
    </div>

    <g:if test="${Util.isPaginationVisible(notificationListTotal, params.max)}">
        <g:paginate total="${notificationListTotal}" class="pagination-centered"/>
    </g:if>
</div>

<div class="span4 sidebar">
    <div class="panel right">
        <h4><g:message code="notification.list.manage.label"/></h4>
        <div class="panel-content">
            <div class="panel-buttons">
                <g:link action="dismiss" class="tms-link btn-link">
                    <i class="icon-ok"></i>
                    <g:message code="notification.dismiss.button" />
                </g:link>
            </div>
        </div>
    </div>
</div>
</body>
</html>
