<g:if test="${notifications && !notifications?.empty}">
    <g:each in="${notifications}" var="notification">
        <g:render template="/taglib/notification/notification"
                  model="[isNew: !notification?.seen, message: message(code: notification?.feed?.messageCode, args: notification?.feed?.args)]"/>
    </g:each>
    <g:if test="${showMoreButton}">
        <div><small><g:link action="list" controller="notification"><g:message code="notification.more.button"/></g:link></small></div>
    </g:if>
</g:if>
<g:else>
    <div><g:message code="notification.no.notifications"/></div>
</g:else>